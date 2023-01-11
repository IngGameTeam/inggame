package io.github.inggameteam.inggame.minigame.wrapper.player

import io.github.inggameteam.inggame.component.delegate.Delegate
import java.util.*

class GPlayer(delegate: Delegate) : Delegate by delegate {

    var joinedGame: UUID? by nullable

}