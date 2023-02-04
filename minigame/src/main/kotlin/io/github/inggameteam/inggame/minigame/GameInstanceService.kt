package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ContainerHelper
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.minigame.base.GPlayer
import io.github.inggameteam.inggame.minigame.base.Game
import io.github.inggameteam.inggame.minigame.base.GameServer
import io.github.inggameteam.inggame.minigame.event.GameLoadEvent
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
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
