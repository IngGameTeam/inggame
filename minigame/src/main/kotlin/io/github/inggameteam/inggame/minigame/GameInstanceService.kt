package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
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
import org.koin.core.component.get
import org.koin.core.qualifier.named

class GameInstanceService(
    private val server: GameServer,
    gamePlayerService: GamePlayerService,
    playerService: PlayerService,
    val plugin: IngGamePlugin
) : KoinComponent {

    private val component: ComponentService = get(named("game-player"))
    private val containerComponentService: ContainerComponentService = ContainerComponentServiceImp(
        component as LayeredComponentService, gamePlayerService,
        GPlayer::joinedGame.name, Game::gameJoined.name
    )

    init {
        server.hub = component.get(create(randomUUID(),  server::hub.name), ::GameImp)
    }

    fun create(container: Any, name: Any) = containerComponentService.create(container, name)

}