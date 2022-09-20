package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

interface NoAnvilRename : Game {

    @Suppress("unused")
    @EventHandler
    fun onAnvilRename(event: InventoryClickEvent) {
        if (event.inventory.type == InventoryType.ANVIL) {
            if(event.slotType == InventoryType.SlotType.RESULT) {
                val player = event.whoClicked
                if (player !is Player) return
                if (isJoined(player) && gameState !== GameState.WAIT) {
                    event.isCancelled = true
                }
            }
        }

    }
}