package io.github.inggameteam.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

interface SpawnOnJoin : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun onJoinSpawn(event: GameJoinEvent) {
        val player = event.player
        if (isJoined(player)) {
            Bukkit.getPluginManager().callEvent(GPlayerSpawnEvent(player))
        }
    }

}