package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

interface PersonalTimeOnSpawn : Game, Sectional {

    @Suppress("unused")
    @EventHandler
    fun onGameJoin(event: GameJoinEvent) {
        val player = event.player
        if (isJoined(player)) {
            setPlayerTime(player)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onSpawnSetPersonalTime(event: GPlayerSpawnEvent) {
        val player = event.player
        if (isInSector(player.location)) {
            setPlayerTime(player)
        } else {
            player.resetPlayerTime()
        }
    }

    private fun setPlayerTime(player: GPlayer) {
        player.setPlayerTime(comp.intOrNull("time")?.toLong()?: return, false)
    }

}