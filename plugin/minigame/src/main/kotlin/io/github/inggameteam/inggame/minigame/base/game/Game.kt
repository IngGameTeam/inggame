package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.utils.ITask
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

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