package io.github.inggameteam.minigame

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.utils.IntVector
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.Listener

interface Game : Listener {

    val isAllocated: Boolean
    val point: IntVector
    var gameState: GameState
    val name: String
    val playerData: HashMap<GPlayer, HashMap<String, Any>>
    val joined: GPlayerList

    var gameTask: ITask?
    fun cancelGameTask()


    val startPlayersAmount: Int
    val startWaitingTick: Int
    val stopWaitingTick: Long

    fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean
    fun joinGame(gPlayer: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean
    fun leftGame(gPlayer: GPlayer, leftType: LeftType)
    fun finishGame(leftType: LeftType)
    fun start(force: Boolean)
    fun stop(force: Boolean, leftType: LeftType = LeftType.GAME_STOP)
    fun calcWinner()

}