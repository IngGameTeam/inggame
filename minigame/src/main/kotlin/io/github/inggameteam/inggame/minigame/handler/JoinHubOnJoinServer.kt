package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.minigame.GameService
import io.github.inggameteam.inggame.minigame.wrapper.Server
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class JoinHubOnJoinServer(
    private val server: Server, plugin: Plugin,
    private val gameService: GameService
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        gameService.join(server.hub, event.player.uniqueId)
    }

    @Suppress("unused")
    @EventHandler
    fun onLeft(event: PlayerQuitEvent) {
        gameService.left(event.player.uniqueId)
    }

}