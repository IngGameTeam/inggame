package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.plugin.Plugin

class NoUnderWaterFall(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBlockMove(event: BlockFromToEvent) {
        val toBlock = event.toBlock
        if (toBlock.type === Material.WATER) {
            if (toBlock.y <= 0) {
                event.isCancelled = true
            }
        }
    }


}