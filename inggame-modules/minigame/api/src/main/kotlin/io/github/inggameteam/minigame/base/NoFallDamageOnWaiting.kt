package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

interface NoFallDamageOnWaiting : Sectional {

    @EventHandler
    fun noDamageOnWaiting(event: EntityDamageEvent) {
        if (gameState === GameState.WAIT && event.entityType === EntityType.PLAYER && isJoined(event.entity as Player)
            && event.cause === EntityDamageEvent.DamageCause.FALL)
            event.isCancelled = true
    }



}