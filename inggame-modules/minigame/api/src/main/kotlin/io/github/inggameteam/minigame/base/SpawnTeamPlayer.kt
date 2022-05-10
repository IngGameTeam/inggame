package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent

interface SpawnTeamPlayer : SpawnPlayer {

    override fun spawnPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        val gPlayer = plugin[player]
        if (gPlayer.hasTag(PTag.RED)) spawn(player, PTag.RED.toString())
        else if (gPlayer.hasTag(PTag.BLUE)) spawn(player, PTag.BLUE.toString())
    }

}