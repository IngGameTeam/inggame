package io.github.inggameteam.minigame

import io.github.inggameteam.minigame.GameAlert.*
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.minigame.event.GameTaskCancelEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent

abstract class GameImpl(
    override val plugin: GamePlugin,
    override val point: Sector,
    ) : Game {
    override val isAllocated: Boolean get() = !point.equals(0, 0)
    override var gameState = GameState.WAIT
    override var gameTask: ITask? = null
    override val playerData = HashMap<GPlayer, HashMap<String, Any>>()
    override val joined = GPlayerList()

    override val startPlayersAmount = 1
    override val startWaitingSecond = 4
    override val stopWaitingTick = -1L

    override fun toString() = name
    override fun cancelGameTask() {
        val gameTask = gameTask
        gameTask?.cancel()
        Bukkit.getPluginManager().callEvent(GameTaskCancelEvent(this))
    }

    override fun addTask(task: ITask) {
        gameTask?.tasks?.addAll(task.tasks).apply {
            if (this === null) gameTask = task
        }
    }

    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
        if (joined.contains(gPlayer)) {
            if (sendMessage) comp.send(ALREADY_JOINED, gPlayer, this)
        } else if (gameState !== GameState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) comp.send(CANNOT_JOIN_DUE_TO_STARTED, gPlayer, this)
        } else {
            return true
        }
        return false
    }

    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
        if (requestJoin(gPlayer, joinType, true)) {
            joined.add(gPlayer)
            playerData[gPlayer] = HashMap()
            comp.send(JOIN, gPlayer, this)
            if (joinType === JoinType.PLAY) gPlayer.addTag(PTag.PLAY)
            else comp.send(START_SPECTATING, gPlayer, gPlayer, this)
            Bukkit.getPluginManager().callEvent(GameJoinEvent(gPlayer, this, joinType))
            if (gameTask === null && gameState === GameState.WAIT && 0 < startPlayersAmount && joined.hasTags(
                    PTag.PLAY).size >= startPlayersAmount
            ) start(false)
            return true
        }
        return false
    }

    override fun requestLeft(gPlayer: GPlayer, leftType: LeftType) = joined.contains(gPlayer)

    override fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean {
        if (!requestLeft(gPlayer, leftType)) return false
        Bukkit.getPluginManager().callEvent(GameLeftEvent(gPlayer, this, leftType))
        joined.remove(gPlayer)
        playerData.remove(gPlayer)
        gPlayer.clearTags()
        if (leftType === LeftType.LEFT_SERVER) {
            comp.send(LEFT_GAME_DUE_TO_SERVER_LEFT, gPlayer, this)
        } else {
            comp.send(LEFT, gPlayer, this)
        }
        val joinedSize = joined.hasTags(PTag.PLAY).size
        if (gameState === GameState.WAIT && joinedSize < startPlayersAmount && gameTask != null) {
            comp.send(START_CANCELLED_DUE_TO_PLAYERLESS, joined)
            cancelGameTask()
            gameTask = null
        }
        if (leftType.isJoinHub) plugin.gameRegister.join(gPlayer, plugin.gameRegister.hubName)
        if (gameState != GameState.STOP && joinedSize <= if (gameState === GameState.PLAY) 1 else 0) {
            stop(false)
        }
        return true
    }

    override fun start(force: Boolean) {
        if (force) {
            gameState = GameState.PLAY
            afterParticle()
            beginGame()
            Bukkit.getPluginManager().callEvent(GameBeginEvent(this))
        } else if (gameState === GameState.WAIT) {
            val tick = startWaitingSecond
            if (tick < 0) {
                start(true)
            } else {
                val list = ArrayList<() -> Unit>()
                for (i in tick downTo  1) list.add { comp.send(GAME_START_COUNT_DOWN, joined, this, i) }
                list.add { gameTask = null; start(true) }
                gameTask = ITask.repeat(plugin, 20, 20, *(list.toTypedArray()))
            }
        }
    }

    override fun stop(force: Boolean, leftType: LeftType) {
        if (gameState !== GameState.STOP) {
            finishGame(leftType)
            if (gameState === GameState.STOP)
                gameTask = { gameTask = null; plugin.gameRegister.removeGame(this) }.delay(plugin, stopWaitingTick)
        }
    }

    override fun finishGame(leftType: LeftType) {
        gameState = GameState.STOP
        ArrayList(joined).forEach { gPlayer ->
            leftGame(gPlayer, leftType)
        }
        gameTask = null
    }

    open fun beginGame() {
        comp.send(GAME_START, joined, this)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val gPlayer = plugin[event.player]
        if (requestLeft(gPlayer, LeftType.LEFT_SERVER)) leftGame(gPlayer, LeftType.LEFT_SERVER)
    }

    open fun afterParticle() {
        joined.hasTags(PTag.PLAY).forEach {
            it.world.spawnParticle(Particle.END_ROD, it.eyeLocation.clone(), 20)
        }
    }


}