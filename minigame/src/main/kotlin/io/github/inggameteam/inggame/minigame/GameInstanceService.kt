package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ContainerComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.randomUUID
import org.koin.core.component.KoinComponent

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    gameInstanceRepository: GameInstanceRepository,
    @Suppress("unused")
    private val playerService: PlayerService,
    val plugin: IngGamePlugin,
) : KoinComponent, LayeredComponentService by gameInstanceRepository,
    ContainerComponentService<Game, GPlayer> by ContainerComponentServiceImp(
        gameInstanceRepository, gamePlayerService,
        GPlayer::joinedGame.name, Game::gameJoined.name
    )

{

    init {
        server.hub = get(randomUUID(), ::GameImp).apply { create(this, server::hub.name) }
    }

}