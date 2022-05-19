package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

interface NoDamage : Game {

    @Suppress("unused")
    @EventHandler
    fun noDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (
            gameState === GameState.PLAY
            && entity is Player && isJoined(entity)
            && entity.health > event.finalDamage
        )
            event.isCancelled = true
    }


}