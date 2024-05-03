package io.github.realyusufismail.core.init

import io.github.realyusufismail.TutorialMod.TutorialMod.MOD_ID
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ItemInit {
    @JvmField
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(MOD_ID)

    val EXAMPLE_ITEM: DeferredItem<Item> = ITEMS.register("example_item") { -> Item(Item.Properties()) }

    val RAW_EXAMPLE: DeferredItem<Item> = ITEMS.register("raw_example") { -> Item(Item.Properties()) }
}