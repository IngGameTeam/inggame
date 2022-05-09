package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.player.GPlayer

interface SpawnTeamPlayer : SpawnPlayer {

    override fun spawn(player: GPlayer, spawn: String) {
        defaultGameMode()?.apply { player.gameMode = this }
        fun tpIf(key: String) = getLocationOrNull(key)?.apply { player.teleport(this) } !== null
        fun kitIf(key: String) =
            comp.inventoryOrNull(key, player.lang(plugin))?.apply { player.inventory.contents = contents } !== null

        val map = listOf(PTag.RED, PTag.BLUE, PTag.PLAY).map(Any::toString)
        map.any { tpIf(it) }
        map.any { kitIf(it) }
    }

}