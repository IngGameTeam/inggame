package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.player.GPlayer
import org.bukkit.Location

interface SpawnPlayerRandomLocation : SpawnPlayerRandomKit {

    override fun tpSpawn(player: GPlayer, spawn: String): Location? {
        return super.tpSpawn(player, comp.stringListOrNull("location", player.lang(plugin))?.random()?: spawn)
    }
}