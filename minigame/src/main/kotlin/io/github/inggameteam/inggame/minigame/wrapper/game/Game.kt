package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.Sector
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.ITask
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

@PropWrapper
interface Game : Wrapper {

    val uuid: UUID

    val gameName: String
    val gameInfo: String
    val startPlayersAmount      : Int
    val playerLimitAmount       : Int
    val startWaitingSecond      : Int
    val stopWaitingTick         : Int

    var gameJoined: CopyOnWriteArraySet<GPlayer>
    var gameSector: Sector

    fun addTask(task: ITask)

    fun cancelGameTask()
    fun hasGameTask(): Boolean

    val isAllocatedGame: Boolean
    var gameState: GameState



}