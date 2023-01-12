package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class PrintOnMove(val playerService: PlayerService, plugin: IngGamePlugin) : HandleListener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player.uniqueId
        if (playerService.getParents(player).contains(javaClass.simpleName)) {
            event.player.sendMessage("YE")
        }
    }

}