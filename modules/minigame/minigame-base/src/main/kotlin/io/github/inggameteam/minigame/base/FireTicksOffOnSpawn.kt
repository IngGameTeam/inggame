package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface FireTicksOffOnSpawn : Game {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onSpawnFireTicksOff(event: GPlayerSpawnEvent) {
        val player = event.player
        if (isJoined(player)) {
            player.fireTicks = 0
        }
    }

}