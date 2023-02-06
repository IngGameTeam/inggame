package io.github.inggameteam.inggame.minigame.base.gameserver.hub

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.GameHelper
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.minigame.base.game.LeftType
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class QuitGameOnQuitServer(
    private val gameServer: GameServer, plugin: IngGamePlugin,
    private val gamePlayerService: GamePlayerService,
    private val gameHelper: GameHelper
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        if (isNotHandler(gameServer)) return
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onKick(event: PlayerKickEvent) {
        if (isNotHandler(gameServer)) return
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        gameHelper.leftGame(player, LeftType.LEFT_SERVER)
    }

}