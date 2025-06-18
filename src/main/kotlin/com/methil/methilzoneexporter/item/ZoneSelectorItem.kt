package com.methil.methilzoneexporter.item

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.registries.BuiltInRegistries
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ZoneSelectorItem(properties: Properties) : Item(properties) {

    private var lastClickTime: Long = 0
    private var firstClickPos: BlockPos? = null

    override fun useOn(context: UseOnContext): InteractionResult {
        val clickedPos = context.clickedPos
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastClickTime > 500) {
            firstClickPos?.let { firstPos ->
                onZoneSelected(context, firstPos, clickedPos)
            }
            firstClickPos = clickedPos
        }
        lastClickTime = currentTime
        return InteractionResult.SUCCESS
    }

    // Log les blocs dans la zone entre le premier et le second clic
    private fun onZoneSelected(context: UseOnContext, firstClickPos: BlockPos, secondClickPos: BlockPos) {
        val minPos = BlockPos(
            minOf(firstClickPos.x, secondClickPos.x),
            minOf(firstClickPos.y, secondClickPos.y),
            minOf(firstClickPos.z, secondClickPos.z)
        )
        val maxPos = BlockPos(
            maxOf(firstClickPos.x, secondClickPos.x),
            maxOf(firstClickPos.y, secondClickPos.y),
            maxOf(firstClickPos.z, secondClickPos.z)
        )

        println("Zone selected: Min $minPos, Max $maxPos")

        // Sauvegarde les blocs dans la zone sous forme de tableau 3D
        saveBlocksInZone(context, minPos, maxPos)
    }

    // Sauvegarde tous les blocs dans la zone sous forme de tableau 3D et les écrit dans un fichier .txt
    private fun saveBlocksInZone(context: UseOnContext, minPos: BlockPos, maxPos: BlockPos) {
        val blocksArray = mutableListOf<MutableList<MutableList<String>>>()

        for (x in minPos.x..maxPos.x) {
            val yList = mutableListOf<MutableList<String>>()
            for (y in minPos.y..maxPos.y) {
                val zList = mutableListOf<String>()
                for (z in minPos.z..maxPos.z) {
                    val pos = BlockPos(x, y, z)
                    val state: BlockState = context.level.getBlockState(pos)
                    val blockId = BuiltInRegistries.BLOCK.getKey(state.block).toString() ?: "unknown"
                    zList.add(blockId)
                }
                yList.add(zList)
            }
            blocksArray.add(yList)
        }

        // Écriture dans un fichier .txt
        writeBlocksToFile(context, blocksArray)
    }

    private fun writeBlocksToFile(context: UseOnContext, blocksArray: List<List<List<String>>>) {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
        val directory = Paths.get("exports")
        val fileName = "exported_blocks_$timestamp.txt"
        val filePath = directory.resolve(fileName)

        val lines = mutableListOf<String>()

        for (yList in blocksArray) {
            for (zList in yList) {
                val processedZList = zList.map { block ->
                    if (block == "minecraft:air" || block == "minecraft:void_air") "" else block
                }
                lines.add(processedZList.joinToString(", "))
            }
            lines.add("")
        }

        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory)
            }

            Files.write(filePath, lines)
            context.player?.sendSystemMessage(Component.literal("Les blocs ont été enregistrés dans ${filePath}"))
            println("Les blocs ont été enregistrés dans ${filePath}")
        } catch (e: Exception) {
            context.player?.sendSystemMessage(Component.literal("Erreur lors de l'enregistrement : ${e.message}"))
            println("Erreur lors de l'enregistrement : ${e.message}")
        }
    }
}
