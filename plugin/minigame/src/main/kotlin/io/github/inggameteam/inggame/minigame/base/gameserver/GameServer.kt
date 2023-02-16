package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.GameImp

class GameServer(wrapper: Wrapper) : Wrapper by wrapper {

    var hub: GameImp by nonNull
    var gameWorld: String by default { "customized_game_world" }

}