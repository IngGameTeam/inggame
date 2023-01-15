package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.component.delegate.getAll
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.randomUUID

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    private val playerService: PlayerService,
    component: ComponentService,
) : ContainerComponentService by ContainerComponentServiceImp(
    component as LayeredComponentService, gamePlayerService,
    GPlayer::joinedGame.name, Game::gameJoined.name),
    LayeredComponentService by component as LayeredComponentService {


    init {
        server.hub = component.get(create(randomUUID(),  server::hub.name), ::Game)
    }

}