package io.github.inggameteam.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasTags
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

interface SpawnOnStart : Game {

    @Suppress("unused")
    @EventHandler
    fun onStartSpawn(event: GameBeginEvent) {
        if (event.game !== this) return
        joined.hasTags(PTag.PLAY).forEach { player ->
            Bukkit.getPluginManager().callEvent(GPlayerSpawnEvent(player))
        }
    }

}