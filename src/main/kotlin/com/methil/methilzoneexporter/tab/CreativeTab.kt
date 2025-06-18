package com.methil.methilzoneexporter.tab

import com.methil.methilzoneexporter.MethilZoneExporter.Companion.MODID
import com.methil.methilzoneexporter.item.MethilItem.ITEMS
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier


object CreativeTab {

    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID)

    val EXAMPLE_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_MODE_TABS.register("creative_tab",
        Supplier {
            CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.methil.methilmod")) //The language key for the title of your CreativeModeTab
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .displayItems { parameters: ItemDisplayParameters?, output: CreativeModeTab.Output ->
                    val addedItems = HashSet<Item>()
                    ITEMS.getEntries()
                        .stream()
                        .map { item -> item.get().asItem() }
                        .filter(addedItems::add)
                        .forEach(output::accept)
                }.build()
        })


    fun register(modEventBus: IEventBus){
        CREATIVE_MODE_TABS.register(modEventBus)
    }
}