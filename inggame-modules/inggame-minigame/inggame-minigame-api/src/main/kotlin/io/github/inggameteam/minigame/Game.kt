package io.github.inggameteam.minigame

import io.github.inggameteam.alert.tree.Comp
import io.github.inggameteam.alert.tree.CompDir
import io.github.inggameteam.alert.tree.CompFile
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.scheduler.ITask
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


    val startPlayersAmount: Int
    val startWaitingTick: Int
    val stopWaitingTick: Long

    fun requestJoin(gPlayer: GPlayer, joinType: JoinType, sendMessage: Boolean): Boolean
    fun joinGame(gPlayer: GPlayer, joinType: JoinType = JoinType.PLAY): Boolean
    fun requestLeft(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun leftGame(gPlayer: GPlayer, leftType: LeftType): Boolean
    fun finishGame(leftType: LeftType)
    fun start(force: Boolean)
    fun stop(force: Boolean, leftType: LeftType = LeftType.GAME_STOP)
//    fun calcWinner()


    fun isJoined(player: Player) = joined.contains(player)


    fun <T> comp(getter: (CompDir) -> Comp<CompFile<T>>, lang: String = plugin.defaultLanguage, key: String) =
        plugin.components.langComp(getter, lang, key, name, plugin.gameRegister.hubName)

    fun alert(key: String) = comp(CompDir::alert, key = key)
    fun alert(key: Enum<*>) = alert(key.name)

}