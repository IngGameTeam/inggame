package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.inventory.Inventory
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

interface SpawnPlayer : Game, Sectional {

    @EventHandler
    fun spawnPlayer(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        spawn(player)
    }

    fun spawn(player: GPlayer, spawn: String = gameState.toString()) {
        listOf(
            ::tpSpawn,
            ::potionSpawn,
            ::inventorySpawn,
            ::gameModeSpawn,
        ).forEach { it(player, spawn) }
    }

    fun potionSpawn(player: GPlayer, spawn: String) {
        if (!player.hasTag(PTag.PLAY)) {
            return
        }
        comp.stringListOrNull(spawn, player.lang(plugin))?.forEach {
            val args = it.split(" ")
            player.addPotionEffect(
                PotionEffect(PotionEffectType.getByName(args[0])!!, args[1].toInt(), args[2].toInt())
            )
        }
    }

    fun inventorySpawn(player: GPlayer, spawn: String = this.gameState.toString()): Inventory? {
        if (!player.hasTag(PTag.PLAY)) {
            player.inventory.clear()
            player.inventory.remove(player.itemOnCursor)
            return null
        }
        return comp.inventoryOrNull(spawn, player.lang(plugin))?.apply { player.inventory.contents = contents }
    }

    fun gameModeSpawn(player: GPlayer, spawn: String) {
        if (!player.hasTag(PTag.PLAY)) {
            player.gameMode = GameMode.SPECTATOR
            return
        }
        if (gameState !== GameState.WAIT) defaultGameMode()?.apply { player.gameMode = this }
    }

    fun tpSpawn(player: GPlayer, spawn: String) = getLocationOrNull(spawn)?.apply { player.teleport(this) }

    fun defaultGameMode() =
        when(comp.intOrNull("game-mode")) {
            0 -> GameMode.SURVIVAL
            1 -> GameMode.CREATIVE
            2 -> GameMode.ADVENTURE
            3 -> GameMode.SPECTATOR
            else -> null
        }


}
