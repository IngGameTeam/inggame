package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.plugin.Plugin

class EventTest(plugin: IngGamePlugin) : HandleListener(plugin) {

    @EventHandler
    fun onItemHeld(event: InventoryClickEvent ) {
        val player = event.whoClicked as Player
        player.sendMessage("PlayerItemHeldEvent{action=${event.action}}")
    }
}