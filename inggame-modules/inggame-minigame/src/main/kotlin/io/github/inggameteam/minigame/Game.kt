package io.github.inggameteam.minigame

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.utils.Intvector
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.Listener

interface Game : Listener {

    val isAllocated: Boolean
    val point: Intvector
    var gameState: GameState
    val name: String
    val playerData: HashMap<GPlayer, HashMap<String, Any>>
    fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean
    fun joinGame(gPlayer: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean
    fun leftGameCheck(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun leftGame(gPlayer: GPlayer, leftType: LeftType)
    fun start(force: Boolean)
    fun stop(force: Boolean, leftType: LeftType = LeftType.GAME_STOP)
    val joined: GPlayerList
    fun startPlayersAmount(): Int
    fun startWaitingTick(): Int
    fun stopWaitingTick(): Long
    fun rewardPoint(): Int
    fun calcWinner()
    fun cancelGameTask()
    var gameTask: ITask?
    fun finishGame(leftType: LeftType)

    companion object {
        val world: World? get() = Bukkit.getWorld(WORLD)
        const val SIZE = 300
        const val HEIGHT = 128
        const val WORLD = "customized_ang"
    }

}