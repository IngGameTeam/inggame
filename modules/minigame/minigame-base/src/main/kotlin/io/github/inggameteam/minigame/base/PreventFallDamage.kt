package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

interface PreventFallDamage : Game {

    @Suppress("unused")
    @EventHandler
    fun preventFallDamage(event: EntityDamageEvent) {
        if (event.entityType !== EntityType.PLAYER) return
        val player = plugin[event.entity as Player]
        if (isJoined(player) && event.cause === EntityDamageEvent.DamageCause.FALL)
            event.isCancelled = true
    }

}