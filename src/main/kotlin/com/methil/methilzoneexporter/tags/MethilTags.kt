package com.methil.methiltemplate.data.init


import com.methil.methilzoneexporter.MethilZoneExporter.Companion.MODID
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object MethilTags {
    fun createOreLocation(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MODID, "ores/$name")
    }

    fun createBlockLocation(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MODID, name)
    }

    fun createGenericItemsLocation(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MODID, name)
    }

    fun createRawItemsLocation(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MODID, "raw/$name")
    }

    fun createToolTag(name: String): TagKey<Block> {
        return TagKey.create<Block>(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(MODID, name)
        )
    }

    object ItemTagsInit {

    }

    object BlockTagsInit {

    }
}