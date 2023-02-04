package io.github.inggameteam.inggame.minigame.listener

import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockFromToEvent

class NoUnderWaterFall(plugin: IngGamePlugin) : Listener(plugin) {

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