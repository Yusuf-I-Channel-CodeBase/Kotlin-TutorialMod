pluginManagement {
    repositories {
        maven(url = uri("https://maven.neoforged.net/releases"))
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    }
}