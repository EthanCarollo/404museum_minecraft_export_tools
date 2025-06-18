package com.methil.methilzoneexporter

import com.methil.methilzoneexporter.item.MethilItem
import com.methil.methilzoneexporter.tab.CreativeTab
import com.mojang.logging.LogUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.*
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import java.util.function.Consumer


@Mod(MethilZoneExporter.MODID)
class MethilZoneExporter (modEventBus: IEventBus, modContainer: ModContainer) {
    companion object {
        const val MODID = "methilzoneexporter"
        val LOGGER = LogUtils.getLogger();

        fun location(path: String): ResourceLocation {
            return ResourceLocation.fromNamespaceAndPath(MODID, path)
        }
    }

    init {
        modEventBus.addListener(::commonSetup)
        MethilItem.register(modEventBus)
        CreativeTab.register(modEventBus)
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        // Fired when setup is good
        Config.items.forEach(Consumer { item: Item ->
            LOGGER.info(
                "METHIL ITEM >> {}",
                item.toString()
            )
        })
    }

}