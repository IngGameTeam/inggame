package io.github.inggameteam.inggame.minigame.base.gameserver.hub

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

class JoinHubOnJoinServer(
    private val gameServer: GameServer, plugin: IngGamePlugin,
    private val gameInstanceService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    private val playerInstanceService: PlayerInstanceService,
    @Suppress("unused")
    private val playerLoader: PlayerLoader,
    private val gameHelper: GameHelper
) : HandleListener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onIngGamePluginEnable(event: IngGamePluginEnableEvent) {
        if (isNotHandler(gameServer)) return
        playerInstanceService.getAll().map(NameSpace::name)
            .filterIsInstance<UUID>().forEach { joinHub(it) }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        if (isNotHandler(gameServer)) return
        joinHub(event.player.uniqueId)
    }

    private fun joinHub(playerUuid: UUID) {
        val game = gameInstanceService[gameServer.hub, ::GameImp]
        val player = gamePlayerService[playerUuid, ::GPlayer]
        gameHelper.joinGame(game, player, JoinType.PLAY)
    }

}