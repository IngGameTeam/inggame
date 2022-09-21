package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

interface SpawnOnStart : Game {

    @Suppress("unused")
    @EventHandler
    fun onStartSpawn(event: GameBeginEvent) {
        if (event.game !== this) return
        joined.forEach { player ->
            Bukkit.getPluginManager().callEvent(GPlayerSpawnEvent(player))
        }
    }

}