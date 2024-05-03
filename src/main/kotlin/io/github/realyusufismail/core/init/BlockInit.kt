package io.github.realyusufismail.core.init

import io.github.realyusufismail.TutorialMod.TutorialMod.MOD_ID
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister


object BlockInit {
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(MOD_ID)

    val EXAMPLE_BLOCK: DeferredBlock<Block> =
        registerBlock("example_block", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))

    // Normal ores - stops gen at y 0

    val EXAMPLE_ORE: DeferredBlock<Block> =
        registerBlock("example_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE))

    // Deepslate ores - starts gen at y 0

    val DEEPSLATE_EXAMPLE_ORE: DeferredBlock<Block> =
        registerBlock("deepslate_example_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE))

    private fun registerBlock(
        name: String, property: BlockBehaviour.Properties
    ): DeferredBlock<Block> {

        val blockReg = BLOCKS.register(name) { -> Block(property) }
        ItemInit.ITEMS.register(name) { -> BlockItem(blockReg.get(), Item.Properties()) }
        return blockReg
    }
}