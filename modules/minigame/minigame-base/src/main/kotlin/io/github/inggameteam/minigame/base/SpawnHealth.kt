package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler

interface SpawnHealth : Game {

    @Suppress("unused")
    @EventHandler
    fun onSpawnHealth(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        joined.forEach { it.health = it.maxHealth }
    }

}