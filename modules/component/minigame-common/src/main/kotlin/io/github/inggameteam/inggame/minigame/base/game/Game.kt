package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.game.event.GameTaskCancelEvent
import io.github.inggameteam.inggame.utils.ITask
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import org.bukkit.Bukkit
import java.util.*

interface Game : Wrapper {

    val uuid: UUID

    val gameName: String
    val gameInfo: String
    var startPlayersAmount      : Int
    val playerLimitAmount       : Int
    val startWaitingSecond      : Int
    var stopWaitingTick         : Int

    var gameState: GameState
    var gameJoined: SafeSetWithToString<GPlayer>

    fun addTask(task: ITask)

    fun cancelGameTask()
    fun hasGameTask(): Boolean

}

class GameImp(wrapper: Wrapper) : Game, SimpleWrapper(wrapper) {

    override val uuid: UUID get() = nameSpace as UUID

    override val gameName: String by nonNull
    override val gameInfo: String by nonNull
    override var startPlayersAmount      : Int           by nonNull
    override val playerLimitAmount       : Int           by nonNull
    override val startWaitingSecond      : Int           by nonNull
    override var stopWaitingTick         : Int           by nonNull

    override var gameState: GameState by default { GameState.WAIT }
    override var gameJoined: SafeSetWithToString<GPlayer> by default { SafeSetWithToString<GPlayer>() }

    private var gameTask: ITask? by nullable
    override fun cancelGameTask() {
        val gameTask = gameTask
        gameTask?.cancel()
        this.gameTask = null
        Bukkit.getPluginManager().callEvent(GameTaskCancelEvent(this))
    }

    override fun addTask(task: ITask) {
        gameTask?.tasks?.addAll(task.tasks).apply {
            if (this === null) gameTask = task
        }
    }

    override fun hasGameTask() = gameTask !== null

    override fun toString(): String {
        return "Game{name=$gameName}"
    }

}