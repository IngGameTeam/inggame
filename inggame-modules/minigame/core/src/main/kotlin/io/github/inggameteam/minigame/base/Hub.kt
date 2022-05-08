package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import org.bukkit.event.EventHandler

class Hub(plugin: GamePlugin, point: Sector) : Sectional(plugin, point), SpawnOnJoin {

    override fun stop(force: Boolean, leftType: LeftType) = Unit
    override var gameState = GameState.STOP
    override val startPlayersAmount = -1
    override val startWaitingTick = -1
    override val name: String get() = plugin.gameRegister.hubName
    val spawn get() = getLocation("default")

    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        event.player.teleport(spawn)
    }



}
