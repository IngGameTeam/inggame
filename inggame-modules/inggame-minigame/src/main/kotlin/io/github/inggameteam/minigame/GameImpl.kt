package io.github.inggameteam.minigame

import io.github.inggameteam.minigame.GameAlert.*
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameJoinEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.minigame.event.GameTaskCancelEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.IntVector
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent

abstract class GameImpl(
    val plugin: GamePlugin,
    override val point: IntVector,
    ) : Game {
    override val isAllocated get() = true
    override var gameState = GameState.WAIT
    override var gameTask: ITask? = null
    override val playerData = HashMap<GPlayer, HashMap<String, Any>>()
    override val joined = GPlayerList()

    override val startPlayersAmount = 1
    override val startWaitingTick = -1
    override val stopWaitingTick = -1L

    override fun toString() = name
    override fun cancelGameTask() {
        val gameTask = gameTask
        gameTask?.cancel()
        Bukkit.getPluginManager().callEvent(GameTaskCancelEvent(this))
    }

    protected fun comp(alert: String) =
        plugin.components[name]?.alert(alert, plugin.defaultLanguage) ?: plugin.component.alert(alert, plugin.defaultLanguage)
    protected fun comp(alert: Enum<*>) = comp(alert.name)

    override fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean {
        if (joined.contains(gPlayer)) {
            if (sendMessage) comp(ALREADY_JOINED).send(gPlayer, this)
        } else if (gameState !== GameState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) comp(CANNOT_JOIN_DUE_TO_STARTED).send(gPlayer, this)
        } else {
            return true
        }
        return false
    }

    override fun joinGame(gPlayer: GPlayer, joinType: JoinType): Boolean {
        if (requestJoin(gPlayer, joinType, true)) {
            Bukkit.getPluginManager().callEvent(GameJoinEvent(gPlayer, this, joinType))
            joined.add(gPlayer)
            playerData[gPlayer] = HashMap()
            comp(JOIN).send(gPlayer, this)
            if (joinType === JoinType.PLAY) gPlayer.addTag(PTag.PLAY)
            else comp(START_SPECTATING).send(gPlayer, gPlayer, this)
            if (gameTask === null && gameState === GameState.WAIT && 0 < startPlayersAmount && joined.playerHasTags(
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
            comp(LEFT_GAME_DUE_TO_SERVER_LEFT).send(gPlayer, this)
        } else {
            comp(LEFT).send(gPlayer, this)
        }
        val joinedSize = joined.playerHasTags(PTag.PLAY).size
        if (gameState === GameState.WAIT && joinedSize < startPlayersAmount && 0 < startPlayersAmount && gameTask != null) {
            comp(START_CANCELLED_DUE_TO_PLAYERLESS).send(joined)
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
            val tick = startWaitingTick
            if (tick < 0) {
                start(true)
            } else {
                val list = ArrayList<() -> Unit>()
                for (i in tick downTo  1) list.add { comp(GAME_START_COUNT_DOWN).send(joined, this, i) }
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
        comp(GAME_START).send(joined, this)
    }

/*
    override fun calcWinner() {
        val winners = joined.playerHasNoTags(PTag.DEAD).playerHasTags(PTag.PLAY)
        val dieToReady = joined.playerHasTags(PTag.DEAD, PTag.PLAY)
        if (winners.isEmpty() && dieToReady.size == 1) comp(GAME_DRAW_HAS_WINNER).send(joined, winners, this)
        else if (winners.isEmpty()) comp(GAME_DRAW_NO_WINNER).send(joined, this)
        else copm(SINGLE_WINNER).send(joined, winners, this)
        winners.forEach{ Context.rewardPoint(it.player, rewardPoint)}
        Plugin.callEvent(GPlayerWinEvent(this, winners))
    }
*/

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val gPlayer = plugin[event.player]
        if (requestLeft(gPlayer, LeftType.LEFT_SERVER)) leftGame(gPlayer, LeftType.LEFT_SERVER)
    }

    open fun afterParticle() {
        joined.playerHasTags(PTag.PLAY).forEach {
            it.world.spawnParticle(Particle.END_ROD, it.eyeLocation.clone(), 20)
        }
    }


}