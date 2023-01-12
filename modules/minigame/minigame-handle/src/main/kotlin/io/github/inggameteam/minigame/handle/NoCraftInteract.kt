package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.plugin.Plugin

class NoCraftInteract(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onInteract(event: InventoryClickEvent) {
        if (event.inventory.type !== InventoryType.PLAYER) return
        if ((1..4).contains(event.rawSlot)) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onInteract(event: InventoryDragEvent) {
        if (event.inventory.type !== InventoryType.PLAYER) return
        if ((1..4).any { event.rawSlots.contains(it) }) {
            event.isCancelled = true
        }
    }



}