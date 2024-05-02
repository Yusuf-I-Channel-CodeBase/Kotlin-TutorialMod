import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"
    id("net.neoforged.gradle.userdev") version "7.0.109"
    id("idea")
}

project.version = properties["mod_version"] as String
base.archivesName.set("tutorialmod")

val mcVersion = properties["minecraft_version"] as String
val modId = properties["mod_id"] as String

// Mojang ships Java 21 to end users in 1.20.5, so your mod should target Java 21.
java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

runs {
    configureEach {
        systemProperty("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
        systemProperty("forge.logging.console.level", "debug")
        modSource(project.sourceSets.main.get())
    }

    create("client") { systemProperty("forge.enabledGameTestNamespaces", modId) }

    create("server") {
        systemProperty("forge.enabledGameTestNamespaces", modId)
        programArguments.set(listOf("--nogui"))
    }

    create("gameTestServer") { systemProperty("forge.enabledGameTestNamespaces", modId) }

    create("data") {
        workingDirectory(file("run-data"))

        programArguments.addAll(
            "--mod",
            modId,
            "--all",
            "--output",
            file("src/generated/resources/").absolutePath,
            "--existing",
            file("src/main/resources/").absolutePath)
    }
}

sourceSets.main { resources.srcDir("src/generated/resources") }

repositories {
    maven { url = uri("https://thedarkcolour.github.io/KotlinForForge/") }
    mavenCentral()
}

dependencies {
    // Use the latest version of NeoForge
    implementation("net.neoforged:neoforge:" + properties["neoForgeVersion"])

    // Must use the '-neoforge' version on NeoForge. If on regular forge, omit the '-neoforge'
    implementation("thedarkcolour:kotlinforforge-neoforge:" + properties["kotlinForForgeVersion"])
}

// This block of code expands all declared replace properties in the specified resource targets.
// A missing property will result in an error. Properties are expanded using ${} Groovy notation.
// When "copyIdeResources" is enabled, this will also run before the game launches in IDE environments.
// See https://docs.gradle.org/current/dsl/org.gradle.language.jvm.tasks.ProcessResources.html
tasks.withType(ProcessResources::class.java) {
    val replaceProperties =
        mapOf(
            "minecraft_version" to mcVersion,
            "minecraft_version_range" to properties["minecraft_version_range"],
            "neo_version" to properties["neo_version"],
            "neo_version_range" to properties["neo_version_range"],
            "loader_version_range" to properties["loader_version_range"],
            "mod_id" to modId,
            "mod_name" to properties["mod_name"],
            "mod_license" to properties["mod_license"],
            "mod_version" to project.version,
            "mod_authors" to properties["mod_authors"],
            "mod_description" to properties["mod_description"]
        )

    inputs.properties(replaceProperties)

    filesMatching(listOf("META-INF/neoforge.mods.toml")) { expand(replaceProperties) }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Xdiags:verbose"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}