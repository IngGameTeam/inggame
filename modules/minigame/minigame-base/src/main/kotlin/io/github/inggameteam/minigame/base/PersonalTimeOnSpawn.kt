package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.event.EventHandler

interface PersonalTimeOnSpawn : Game, Sectional {

    @Suppress("unused")
    @EventHandler
    fun onSpawnSetPersonalTime(event: GPlayerSpawnEvent) {
        val player = event.player
        if (isInSector(player.location)) {
            player.setPlayerTime(comp.intOrNull("time")?.toLong()?: return, false)
        } else {
            player.resetPlayerTime()
        }
    }

}