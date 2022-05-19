package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class HideJoinLeaveMessage(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) { event.joinMessage = null }

    @Suppress("unused")
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) { event.quitMessage = null }

}