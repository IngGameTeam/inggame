package io.github.inggameteam.minigame

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

interface Game : Listener, PluginHolder<GamePlugin> {

    /**
     *
     */
    val isAllocated: Boolean
    val point: Sector
    var gameState: GameState
    val name: String
    val playerData: HashMap<GPlayer, HashMap<String, Any>>
    val joined: GPlayerList

    var gameTask: ITask?
    fun cancelGameTask()
    fun addTask(task: ITask)


    val startPlayersAmount: Int
    val startWaitingSecond: Int
    val stopWaitingTick: Long

    fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean
    fun joinGame(gPlayer: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean
    fun requestLeft(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun finishGame(leftType: LeftType)
    fun start(force: Boolean)
    fun stop(force: Boolean, leftType: LeftType = LeftType.GAME_STOP)
//    fun calcWinner()
    val comp get() = plugin.components[name]
    fun getLocation(key: String): Location = comp.location(key).toLocation(point.world)
    fun getLocationOrNull(key: String): Location? = comp.locationOrNull(key)?.toLocation(point.world)


    fun isJoined(player: Player) = joined.contains(player)



}