package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

interface SpawnPlayer : Game {

    @EventHandler
    fun spawnPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        spawn(player, gameState.toString())
    }

    private fun spawn(player: GPlayer, spawn: String) {
        getLocationOrNull(spawn)?.apply { player.teleport(this) }
        comp.inventoryOrNull(spawn, player.lang(plugin))?.apply { player.inventory.contents = contents }
    }

}
