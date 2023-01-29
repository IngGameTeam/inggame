package io.github.inggameteam.inggame.player.handler

import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class PlayerLanguage(val playerInstanceService: PlayerInstanceService, plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (event.player.firstPlayed == 0L) {
            playerInstanceService.set(event.player.uniqueId, "language", "english")
        }
    }

}