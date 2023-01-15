package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.component.delegate.getAll
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.minigame.LeftType
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class JoinHubOnJoinServer(
    private val server: GameServer, plugin: IngGamePlugin,
    private val gameService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    private val playerService: PlayerService,
    @Suppress("unused")
    private val playerLoader: PlayerLoader,
    private val gameImpl: GameImpl
) : HandleListener(plugin) {

    init {
        playerService.getAll().map(NameSpace::name)
            .forEach { gameService.join(server.hub, gamePlayerService.get(it, ::GPlayer)) }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        gameService.join(server.hub, gamePlayerService.get(event.player.uniqueId, ::GPlayer))
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        val player = gamePlayerService.get(event.player.uniqueId, ::GPlayer)
        if (player.joinedGame === null) return
        gameImpl.leftGame(player, LeftType.LEFT_SERVER)
    }

}