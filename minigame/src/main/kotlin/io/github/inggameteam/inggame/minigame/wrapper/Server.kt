package io.github.inggameteam.inggame.minigame.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.utils.randomUUID
import java.util.*

class Server(delegate: Delegate) : Delegate by delegate {

    var hub: UUID by default { randomUUID() }

}