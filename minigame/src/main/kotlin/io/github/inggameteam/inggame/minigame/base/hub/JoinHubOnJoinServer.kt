package io.github.inggameteam.inggame.minigame.base.hub

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.NoArgsConstructor
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

@NoArgsConstructor
class JoinHubOnJoinServer(
    private val server: GameServer, plugin: IngGamePlugin,
    private val gameInstanceService: GameInstanceService,
    private val gamePlayerService: GamePlayerService,
    private val playerInstanceService: PlayerInstanceService,
    @Suppress("unused")
    private val playerLoader: PlayerLoader,
    private val gameHelper: GameHelper
) : Listener(plugin) {


    @Suppress("unused")
    @EventHandler
    fun onIngGamePluginEnable(event: IngGamePluginEnableEvent) {
        playerInstanceService.getAll().map(NameSpace::name)
            .filterIsInstance<UUID>().forEach { joinHub(it) }
    }

    private fun joinHub(playerUuid: UUID) {
        val game = gameInstanceService[server.hub, ::GameImp]
        val player = gamePlayerService[playerUuid, ::GPlayer]
        gameHelper.joinGame(game, player, JoinType.PLAY)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        joinHub(event.player.uniqueId)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        if (player.joinedGame === null) return
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onKick(event: PlayerKickEvent) {
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        if (player.joinedGame === null) return
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

}