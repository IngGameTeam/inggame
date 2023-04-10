package io.github.inggameteam.inggame.minigame.base.gameserver.hub

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.GameHelper
import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.player.ContainerState
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
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
        gameServer.hub = gameHelper.createContainer(GameServer::hub.name)
            .apply {
                containerState = ContainerState.STOP
            }
    }

}