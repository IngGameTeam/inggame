package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
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
            ::potionSpawn,
            ::inventorySpawn,
            ::gameModeSpawn,
            ::tpSpawn
        ).forEach { it(player, spawn) }
    }

    fun potionSpawn(player: GPlayer, spawn: String) =
        comp.stringListOrNull(spawn, player.lang(plugin))?.forEach {
            val args = it.split(" ")
            player.addPotionEffect(
                PotionEffect(PotionEffectType.getByName(args[0])!!, args[1].toInt(), args[2].toInt())
            )
        }

    fun inventorySpawn(player: GPlayer, spawn: String) =
        comp.inventoryOrNull(spawn, player.lang(plugin))?.apply { player.inventory.contents = contents }

    fun gameModeSpawn(player: GPlayer, spawn: String) = defaultGameMode()?.apply { player.gameMode = this }

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
