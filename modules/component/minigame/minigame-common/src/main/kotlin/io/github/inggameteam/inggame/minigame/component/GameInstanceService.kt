package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.gameserver.GameServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.player.container.ContainerHelper
import io.github.inggameteam.inggame.player.container.ContainerHelperImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.koin.core.component.KoinComponent

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    gameInstanceRepository: GameInstanceRepository,
    @Suppress("unused")
    private val playerService: PlayerService,
    val plugin: IngGamePlugin,
) : KoinComponent, Listener(plugin),
    LayeredComponentService by gameInstanceRepository,
    ContainerHelper<Game, GPlayer> by ContainerHelperImp(
        gameInstanceRepository, gamePlayerService,
        GPlayer::joinedContainer, Game::containerJoined
    )

