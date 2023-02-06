package io.github.inggameteam.inggame.minigame.base.gameserver.hub

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.base.game.GameHelper
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.minigame.base.game.GameState
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class HubLoader(
    private val gameServer: GameServer,
    private val gameInstanceService: GameInstanceService,
    private val gameHelper: GameHelper,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun onIngGamePluginEnable(event: IngGamePluginEnableEvent) {
        if (isNotHandler(gameServer)) return
        gameServer.hub = gameInstanceService[randomUUID(), ::GameImp]
            .apply {
                gameHelper.createGame(this, GameServer::hub.name)
                gameState = GameState.STOP
            }
    }

}