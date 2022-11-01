package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.JoinType
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.player.GPlayer

open class Hub(plugin: GamePlugin) : SectionalImpl(plugin), FireTicksOffOnSpawn {


    override fun stop(force: Boolean, leftType: LeftType) = Unit
    override var gameState
    get() = GameState.STOP
        set(_) {GameState.STOP}
    override val startPlayersAmount = -1
    override val startWaitingSecond = -1
    override val name: String get() = plugin.gameRegister.hubName
    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean) = true
    override fun unloadSector() = Unit
    override fun loadSector(key: String) = Unit
//    init {
//        if (isAllocated) super.loadDefaultSector()
//    }


}
