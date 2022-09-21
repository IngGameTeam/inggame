package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityPickupItemEvent

interface NoItemPickup : Game {

    @Suppress("unused")
    @EventHandler
    fun noBlockPlace(event: EntityPickupItemEvent) {
        if (event.entityType !== EntityType.PLAYER) return
        val player = event.entity as Player
        if (isJoined(player) && gameState !== GameState.WAIT)
            event.isCancelled = true
    }

}