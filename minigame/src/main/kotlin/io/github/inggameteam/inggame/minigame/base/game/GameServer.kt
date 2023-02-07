package io.github.inggameteam.inggame.minigame.base.game

import io.github.inggameteam.inggame.component.wrapper.Wrapper

class GameServer(wrapper: Wrapper) : Wrapper by wrapper {

    var hub: GameImp by nonNull
    var gameWorld: String by default { "customized_game_world" }

}