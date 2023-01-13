package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class PrintOnMove(private val playerService: PlayerService, plugin: IngGamePlugin) : HandleListener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        event.player.sendMessage("YE1")
        val player = event.player.uniqueId
        println(playerService.get(event.player.uniqueId, ::GPlayer).joinedGame)
        if (playerService.getParents(player).contains(javaClass.simpleName)) {
            event.player.sendMessage("YE")
        }
    }

}