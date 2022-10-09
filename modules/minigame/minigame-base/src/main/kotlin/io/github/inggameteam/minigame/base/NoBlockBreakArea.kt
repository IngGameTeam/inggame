package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.util.Vector

interface NoBlockBreakArea : Game {

    val noBlockBreakArea: ArrayList<Pair<Location, Location>>

    fun readBlockBreakArea() {
        for (keys in comp.stringListOrNull("no-block-break-area", plugin.defaultLanguage)?: return) {
            val pos1 = comp.location("${keys}-pos1", plugin.defaultLanguage).toLocation(world).toVector()
            val pos2 = comp.location("${keys}-pos2", plugin.defaultLanguage).toLocation(world).toVector()
            noBlockBreakArea.add(Pair(
                Vector.getMinimum(pos1, pos2).toLocation(world),
                Vector.getMaximum(pos1, pos2).toLocation(world)
            ))
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        val location = event.block.location.toVector()
        for (pair in noBlockBreakArea) {
            location.isInAABB(pair.first.toVector(), pair.second.toVector())
        }
    }

}