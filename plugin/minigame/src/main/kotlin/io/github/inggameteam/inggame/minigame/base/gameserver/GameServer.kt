package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.game.GameImp

class GameServer(wrapper: Wrapper) : Wrapper by wrapper {

    var hub: Game by nonNull
    var gameWorld: String by default { "customized_game_world" }

}