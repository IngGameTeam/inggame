package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent

interface NoInteract : Game {

    @Suppress("unused")
    @EventHandler
    fun noInteract(event: PlayerInteractEntityEvent) {
        if (gameState !== GameState.WAIT && isJoined(event.player)) event.isCancelled = true
    }

    @Suppress("unused")
    @EventHandler
    fun noDamageEntity(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (player is Player && gameState !== GameState.WAIT && isJoined(player)) event.isCancelled = true
    }


}