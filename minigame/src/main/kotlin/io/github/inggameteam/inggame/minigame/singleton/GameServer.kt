package io.github.inggameteam.inggame.minigame.singleton

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.minigame.wrapper.game.GameImp

class GameServer(wrapper: Wrapper) : Wrapper by wrapper {

    var hub: GameImp by nonNull

}