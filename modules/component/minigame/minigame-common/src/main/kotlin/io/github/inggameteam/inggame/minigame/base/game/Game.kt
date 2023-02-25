package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.event.GameTaskCancelEvent
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.player.container.Container
import io.github.inggameteam.inggame.player.container.ContainerImp
import io.github.inggameteam.inggame.utils.ContainerState
import io.github.inggameteam.inggame.utils.ITask
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import org.bukkit.Bukkit
import java.util.*

interface Game : Wrapper, Container<GPlayer> {

    val uuid: UUID

    val gameInfo: String
    val startWaitingSecond      : Int
    var stopWaitingTick         : Int

    fun addTask(task: ITask)

    fun cancelGameTask()
    fun hasGameTask(): Boolean

}

class GameImp(wrapper: Wrapper) : Game, ContainerImp<GPlayer>(wrapper) {

    override val uuid: UUID get() = nameSpace as UUID

    override val gameInfo: String by nonNull
    override val startWaitingSecond      : Int           by nonNull
    override var stopWaitingTick         : Int           by nonNull

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
        return "Game{name=$containerName}"
    }

}
