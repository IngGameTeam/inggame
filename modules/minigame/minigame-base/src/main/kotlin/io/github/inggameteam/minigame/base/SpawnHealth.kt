package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.event.EventHandler

interface SpawnHealth : Game {

    @Suppress("unused")
    @EventHandler
    fun onSpawnHealth(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        player.health = player.maxHealth
    }

}