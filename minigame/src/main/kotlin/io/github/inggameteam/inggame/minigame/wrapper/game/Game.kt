package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.Sector
import io.github.inggameteam.inggame.minigame.event.GameTaskCancelEvent
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.ITask
import org.bukkit.Bukkit
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

class Game(delegate: Delegate) : Delegate by delegate {

    val uuid: UUID get() = nameSpace as UUID

    val gameName: String by nonNull
    val gameInfo: String by nonNull
    val startPlayersAmount      : Int           by nonNull
    val playerLimitAmount       : Int           by nonNull
    val startWaitingSecond      : Int           by nonNull
    val stopWaitingTick         : Int           by nonNull

    var gameJoined: CopyOnWriteArraySet<GPlayer> by default { CopyOnWriteArraySet<GPlayer>() }
    var gameSector: Sector by default { Sector(0, 0) }

    private var gameTask: ITask? by nullable
    fun cancelGameTask() {
        val gameTask = gameTask
        gameTask?.cancel()
        this.gameTask = null
        Bukkit.getPluginManager().callEvent(GameTaskCancelEvent(this))
    }

    fun addTask(task: ITask) {
        gameTask?.tasks?.addAll(task.tasks).apply {
            if (this === null) gameTask = task
        }
    }

    fun hasGameTask() = gameTask !== null



    val isAllocatedGame: Boolean get() = gameSector.equals(0, 0)
    var gameState: GameState by default { GameState.WAIT }


}
