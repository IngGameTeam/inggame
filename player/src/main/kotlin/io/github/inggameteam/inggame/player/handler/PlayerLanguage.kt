package io.github.inggameteam.inggame.player.handler

import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class PlayerLanguage(val playerInstanceService: PlayerInstanceService, plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (event.player.firstPlayed == 0L) {
            playerService.set(event.player.uniqueId, "language", "english")
        }
    }

}