package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ContainerHelper
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.event.GameLoadEvent
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.koin.core.component.KoinComponent

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    gameInstanceRepository: GameInstanceRepository,
    @Suppress("unused")
    private val playerService: PlayerService,
    val plugin: IngGamePlugin,
) : KoinComponent, HandleListener(plugin),
    LayeredComponentService by gameInstanceRepository,
    ContainerHelper<Game, GPlayer> by gameInstanceRepository.newContainerHelper(gamePlayerService)
{

    private val containerHelper = gameInstanceRepository.containerHelper

    override fun create(container: Game, parent: Any): Game =
        containerHelper.create(container, parent)
            .also { plugin.server.pluginManager.callEvent(GameLoadEvent(container)) }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onIngGamePluginEnable(event: IngGamePluginEnableEvent) {
        server.hub = get(randomUUID(), ::GameImp).apply { create(this, server::hub.name) }
    }

}