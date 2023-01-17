package io.github.inggameteam.inggame.minigame.singleton

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.minigame.wrapper.game.Game

class GameServer(delegate: Delegate) : Delegate by delegate {

    var hub: Game by nonNull

}