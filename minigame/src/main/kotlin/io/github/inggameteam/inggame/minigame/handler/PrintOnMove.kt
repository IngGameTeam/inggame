package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.wrapper.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.system.measureTimeMillis

class PrintOnMove(
    private val server: GameServer,
    private val playerService: PlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        println(server.hub)
        event.player.sendMessage("asdfasdlfkjsdfl")
        val player = event.player.uniqueId
        println(playerService.get(event.player.uniqueId, ::GPlayer).joinedGame)
        println(measureTimeMillis { repeat(500) { javaClass.simpleName } })
        if (playerService.has(player, javaClass.simpleName)) {
            event.player.sendMessage("YE")
        }
    }

}