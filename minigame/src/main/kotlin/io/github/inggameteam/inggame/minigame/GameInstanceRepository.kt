package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerHelper
import io.github.inggameteam.inggame.component.componentservice.ContainerHelperImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.game.Game

class GameInstanceRepository(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
{

    lateinit var containerHelper: ContainerHelper<Game, GPlayer>

    fun newContainerHelper(gamePlayerService: GamePlayerService) =
        ContainerHelperImp<Game, GPlayer>(
            this, gamePlayerService,
            GPlayer::joinedGame.name, Game::gameJoined.name
        ).also { containerHelper = it }

}