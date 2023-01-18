package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.plugin.Plugin

class EventTest(plugin: Plugin) : HandleListener(plugin) {

    @EventHandler
    fun onItemHeld(event: PlayerItemHeldEvent) {
        event.player.sendMessage("PlayerItemHeldEvent")
    }
}