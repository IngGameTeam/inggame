package io.github.inggameteam.inggame.minigame.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.utils.randomUUID
import java.util.*

class GameServer(delegate: Delegate) : Delegate by delegate {

    var hub: UUID by nonNull

}