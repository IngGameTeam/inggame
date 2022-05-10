package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.player.GPlayer

interface SpawnPlayerRandomKit : SpawnPlayer {

    override fun spawn(player: GPlayer, spawn: String) {
        super.spawn(player, comp.stringListOrNull("spawn", player.lang(plugin))?.random()?: spawn)
    }

}