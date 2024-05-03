package io.github.realyusufismail.core.init

import io.github.realyusufismail.TutorialMod.TutorialMod.MOD_ID
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object CreativeModeTabInit {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID)

    val TUTORIAL_MOD_TAB_TITLE: String = "creativetab.tutorialmod"

    val TUTORIAL_MOD_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> =
        CREATIVE_MODE_TABS.register("tutorial_mod_tab") { ->

            val builder = CreativeModeTab.builder()

            builder.displayItems { itemDisplayParameters, output ->
                ItemInit.ITEMS.entries
                    .map { it.get() }
                    .forEach(output::accept)

                BlockInit.BLOCKS.entries
                    .map { it.get() }
                    .forEach(output::accept)
            }

            builder.icon { ItemStack(ItemInit.EXAMPLE_ITEM.get()) }
            builder.title(Component.translatable(TUTORIAL_MOD_TAB_TITLE))

            builder.build()
        }
}