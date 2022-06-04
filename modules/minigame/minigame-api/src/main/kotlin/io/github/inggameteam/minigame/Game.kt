package io.github.inggameteam.minigame

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import kotlin.test.assertNotNull

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
    val playerLimitAmount: Int
    val startWaitingSecond: Int
    val stopWaitingTick: Long

    fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean
    fun joinGame(gPlayer: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean
    fun requestLeft(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun finishGame()
    fun start(force: Boolean)
    fun stop(force: Boolean, leftType: LeftType = LeftType.GAME_STOP)
    fun requestStop()

    val worldName get() = comp.stringOrNull("world", plugin.defaultLanguage)
        ?: plugin.gameRegister.worldName.firstOrNull().apply { assertNotNull(this, "world list is empty") }!!
    val world get() = Bukkit.getWorld(worldName).apply { assertNotNull(this, "world $worldName is not loaded") }!!

    val comp get() = plugin.components[name]

    fun isJoined(player: Player) = isJoined(plugin[player])
    fun isJoined(player: GPlayer) = joined.contains(player)

    /**
     * get name of game of player's language
     */
    fun displayName(player: GPlayer) = displayName(player.lang(plugin))

    fun displayName(lang: String) = comp.stringOrNull("alias", lang)?: name

}