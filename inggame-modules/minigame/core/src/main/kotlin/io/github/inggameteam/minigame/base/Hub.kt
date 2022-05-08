package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler

class Hub(plugin: GamePlugin, point: Sector) : Sectional(plugin, point), SpawnOnJoin {

    override fun stop(force: Boolean, leftType: LeftType) = Unit
    override var gameState
    get() = GameState.STOP
    set(_) {GameState.STOP}
    override val startPlayersAmount = -1
    override val startWaitingTick = -1
    override val name: String get() = plugin.gameRegister.hubName
    val spawn get() = getLocation("default")
    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean) = true

    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        event.player.teleport(spawn)
    }



}
