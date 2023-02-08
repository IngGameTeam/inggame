package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginDisableEvent
import org.bukkit.event.EventHandler

class UnloadGameOnDisableServer(
    private val gameInstanceService: GameInstanceService,
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onIngGamePluginDisable(event: IngGamePluginDisableEvent) {
        gameInstanceService.getAll(::GameImp)
        plugin.server.broadcastMessage("GameDisable")
        println("GameDisable")
    }

}