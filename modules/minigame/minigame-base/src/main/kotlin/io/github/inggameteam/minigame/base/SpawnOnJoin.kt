package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface SpawnOnJoin : Game {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoinSpawn(event: GameJoinEvent) {
        val player = event.player
        if (event.join === this) {
            Bukkit.getPluginManager().callEvent(GPlayerSpawnEvent(player))
        }
    }

}