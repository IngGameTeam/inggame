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
    var startPlayersAmount      : Int
    val playerLimitAmount       : Int
    val startWaitingSecond      : Int
    var stopWaitingTick         : Int

    var gameState: GameState
    var gameJoined: CopyOnWriteArraySet<GPlayer>

    fun addTask(task: ITask)

    fun cancelGameTask()
    fun hasGameTask(): Boolean




}