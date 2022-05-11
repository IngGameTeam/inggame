package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.player.GPlayer

open class Hub(plugin: GamePlugin) : SectionalImpl(plugin) {


    override fun stop(force: Boolean, leftType: LeftType) = Unit
    override var gameState
    get() = GameState.STOP
        set(_) {GameState.STOP}
    override val startPlayersAmount = -1
    override val startWaitingSecond = -1
    override val name: String get() = plugin.gameRegister.hubName
    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean) = true
    override fun unloadSector() = Unit
    init {
        if (isAllocated) super.loadDefaultSector()
    }


}
