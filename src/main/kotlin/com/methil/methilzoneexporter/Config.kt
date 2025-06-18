package com.methil.methilzoneexporter

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.config.ModConfigEvent
import net.neoforged.neoforge.common.ModConfigSpec
import java.util.stream.Collectors

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = MethilZoneExporter.MODID, bus = EventBusSubscriber.Bus.MOD)
object Config {
    private val BUILDER: ModConfigSpec.Builder = ModConfigSpec.Builder()

    private val ITEM_STRINGS: ModConfigSpec.ConfigValue<List<String>> =
        BUILDER.comment("A list of items to log on common setup.").defineListAllowEmpty(
            "items",
            listOf(),
            Config::validateItemName
        )

    val SPEC: ModConfigSpec = BUILDER.build()
    lateinit var items: Set<Item>

    private fun validateItemName(obj: Any): Boolean {
        return obj is String && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(obj))
    }

    @SubscribeEvent
    fun onLoad(event: ModConfigEvent) {
        items = ITEM_STRINGS.get().stream().map { itemName: String? ->
            BuiltInRegistries.ITEM[ResourceLocation.parse(
                itemName
            )]
        }.collect(Collectors.toSet())
    }
}