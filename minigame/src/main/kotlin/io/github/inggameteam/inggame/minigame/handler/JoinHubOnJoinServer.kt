package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.minigame.JoinType
import io.github.inggameteam.inggame.minigame.LeftType
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class JoinHubOnJoinServer(
    private val server: GameServer, plugin: IngGamePlugin,
    private val gameInstanceService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    playerInstanceService: PlayerInstanceService,
    @Suppress("unused")
    private val playerLoader: PlayerLoader,
    private val gameHelper: GameHelper
) : HandleListener(plugin) {

    init {
        playerInstanceService.getAll().map(NameSpace::name)
            .forEach { gameInstanceService.join(server.hub, gamePlayerService.get(it, ::GPlayer)) }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        println("PlayerJoinEvent")
        val game = gameInstanceService.get(server.hub, ::GameImp)
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        gameHelper.joinGame(game, player, JoinType.PLAY)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        if (player.joinedGame === null) return
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onKick(event: PlayerKickEvent) {
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        if (player.joinedGame === null) return
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

}