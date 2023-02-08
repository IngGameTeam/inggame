package io.github.inggameteam.inggame.minigame.base.sectional

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.event.EventHandler

class ClearSectorOnDisableServer(
    private val gameInstanceService: GameInstanceService,
    private val sectionalHelper: SectionalHelper,
    private val gameServer: GameServer,
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onIngGamePluginDisable(event: IngGamePluginEnableEvent) {
        plugin.addDisableEvent {
            if (isHandler(gameServer))
                gameInstanceService.getAll(::SectionalImp)
                    .filter { it.isHandler(SectionalHandler::class) }
                    .forEach { sectionalHelper.unloadSector(it) }
        }
    }

}