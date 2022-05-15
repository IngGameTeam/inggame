package io.github.inggameteam.base

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.player.GPlayer
import org.bukkit.Location

interface SpawnPlayerRandomLocation : SpawnPlayerRandomKit {

    override fun tpSpawn(player: GPlayer, spawn: String): Location? {
        if (gameState === GameState.WAIT) {
            return super.tpSpawn(player, spawn)
        }
        return super.tpSpawn(player, comp.stringListOrNull("location", player.lang(plugin))?.random()?: spawn)
    }
}