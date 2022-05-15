package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

interface SpawnTeamPlayer : SpawnPlayer {

    @EventHandler
    override fun spawnPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val gPlayer = plugin[player]
        if (gameState === GameState.WAIT) {
            super.spawn(gPlayer, gameState.toString())
            return
        }
        spawn(player)
    }

    private fun getTeam(player: GPlayer) =
        if (player.hasTag(PTag.RED)) PTag.RED
        else if (player.hasTag(PTag.BLUE)) PTag.BLUE
        else PTag.PLAY

    override fun spawn(player: GPlayer, spawn: String) {
        super.spawn(player, if (gameState === GameState.WAIT) spawn else getTeam(player).toString())
    }

}