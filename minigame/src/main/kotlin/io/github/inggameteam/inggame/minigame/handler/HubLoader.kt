package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GameInstanceService
import io.github.inggameteam.inggame.minigame.GameState
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class HubLoader(
    private val gameServer: GameServer,
    private val gameInstanceService: GameInstanceService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onIngGamePluginEnable(event: IngGamePluginEnableEvent) {
        gameServer.hub = gameInstanceService.get(randomUUID(), ::GameImp)
            .apply {
                gameInstanceService.create(this, gameServer::hub.name)
                startWaitingSecond = -1
            }
    }

}