package io.github.inggameteam.inggame.minigame.listener

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class HideJoinLeaveMessage(
    plugin: IngGamePlugin
) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
    }

    @Suppress("unused")
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        event.quitMessage = null
    }

}