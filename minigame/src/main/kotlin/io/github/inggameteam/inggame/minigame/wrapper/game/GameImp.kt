package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.SimpleWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.Sector
import io.github.inggameteam.inggame.minigame.event.GameTaskCancelEvent
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.ITask
import org.bukkit.Bukkit
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

@PropWrapper
class GameImp(wrapper: Wrapper) : Game, SimpleWrapper(wrapper) {

    override val uuid: UUID get() = nameSpace as UUID

    override val gameName: String by nonNull
    override val gameInfo: String by nonNull
    override var startPlayersAmount      : Int           by nonNull
    override val playerLimitAmount       : Int           by nonNull
    override val startWaitingSecond      : Int           by nonNull
    override var stopWaitingTick         : Int           by nonNull

    override var gameState: GameState by default { GameState.WAIT }
    override var gameJoined: CopyOnWriteArraySet<GPlayer> by default { CopyOnWriteArraySet<GPlayer>() }

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
        return "Game{}"
    }

}
