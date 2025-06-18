package com.methil.methilzoneexporter.item

import com.methil.methilzoneexporter.MethilZoneExporter.Companion.MODID
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object MethilItem {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(
        MODID
    )

    val ZONE_SELECTOR: DeferredItem<Item> = ITEMS.register("zone_selector", Supplier {
        ZoneSelectorItem(Item.Properties().rarity(Rarity.EPIC))
    })

    fun register(modEventBus: IEventBus){
        ITEMS.register(modEventBus)
    }
}