package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.minigame.GamePlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType

class NoCraftInHub(val plugin: GamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onCraft(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        if (player.openInventory.topInventory.type !== InventoryType.PLAYER) return
        if (plugin.gameRegister.hubName != plugin.gameRegister.getJoinedGame(player).name) return
        val rawSlot = event.rawSlot
        if (rawSlot in 1..4) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onCraft(event: InventoryDragEvent) {
        val player = event.whoClicked as Player
        if (plugin.gameRegister.hubName != plugin.gameRegister.getJoinedGame(player).name) return
        val rawSlots = event.rawSlots
        rawSlots.forEach { rawSlot ->
            if (rawSlot in 1..4) {
                event.isCancelled = true
            }
        }    }

}