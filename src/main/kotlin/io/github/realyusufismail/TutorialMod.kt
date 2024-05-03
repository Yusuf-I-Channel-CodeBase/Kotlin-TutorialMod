package io.github.realyusufismail

import io.github.realyusufismail.TutorialMod.TutorialMod.MOD_ID
import io.github.realyusufismail.core.init.BlockInit
import io.github.realyusufismail.core.init.CreativeModeTabInit
import io.github.realyusufismail.core.init.ItemInit
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import thedarkcolour.kotlinforforge.neoforge.KotlinModLoadingContext

@Mod(MOD_ID)
class TutorialMod {
    init {
        val bus = KotlinModLoadingContext.get().getKEventBus()

        BlockInit.BLOCKS.register(bus)
        ItemInit.ITEMS.register(bus)
        CreativeModeTabInit.CREATIVE_MODE_TABS.register(bus)

        KotlinModLoadingContext.get().getKEventBus().addListener(FMLClientSetupEvent::class.java) {
                event: FMLClientSetupEvent ->
            event.enqueueWork {
                ModList.get().getModContainerByObject(MOD_ID).ifPresent { mod ->
                    logger.info("Loaded {} v{}", mod.modInfo.displayName, mod.modInfo.version)
                }
            }
        }
    }

    companion object TutorialMod {
        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(TutorialMod::class.java)

        const val MOD_ID = "tutorialmod"
    }
}