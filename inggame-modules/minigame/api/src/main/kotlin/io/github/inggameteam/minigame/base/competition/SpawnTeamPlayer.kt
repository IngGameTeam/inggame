package io.github.inggameteam.minigame.base.competition

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.hasTags
import org.bukkit.GameMode
import org.bukkit.event.EventHandler

interface SpawnTeamPlayer : Game {

    @Deprecated("EventHandler")
    @EventHandler
    fun onSpawnTeamPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        comp.intOrNull("game-mode")?.apply { joined.hasTags(PTag.PLAY).forEach { it.gameMode = when(this) {
            1 -> GameMode.CREATIVE
            2 -> GameMode.ADVENTURE
            3 -> GameMode.SPECTATOR
            else -> GameMode.SURVIVAL
        } } }
        fun tpIf(key: String) = getLocationOrNull(key)?.apply { player.teleport(this) } !== null
        fun kitIf(key: String) =
            comp.inventoryOrNull(key, player.lang(plugin))?.apply { player.inventory.contents = contents } !== null

        val map = listOf(PTag.RED, PTag.BLUE, PTag.PLAY).map(Any::toString)
        map.any { tpIf(it) }
        map.any { kitIf(it) }
    }

}