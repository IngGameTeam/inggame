package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.GameResourceService
import io.github.inggameteam.inggame.minigame.wrapper.GameServer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class JoinHubOnJoinServer(
    private val server: GameServer, plugin: IngGamePlugin,
    private val gameService: GameInstanceService,
    private val gameResourceService: GameResourceService,
    private val playerService: PlayerService,
    playerLoader: PlayerLoader
) : HandleListener(plugin) {

    init {
        playerService.getAll().map(NameSpace::name).forEach {
            gameService.join(server.hub, it as UUID)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        gameService.join(server.hub, event.player.uniqueId)
    }

    @Suppress("unused")
    @EventHandler
    fun onLeft(event: PlayerQuitEvent) {
        gameService.left(event.player.uniqueId)
    }

}