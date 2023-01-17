package io.github.inggameteam.inggame.minigame.wrapper.player

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer
import io.github.inggameteam.inggame.utils.TagContainer
import java.util.concurrent.CopyOnWriteArraySet

class GPlayer(delegate: Delegate) : WrappedPlayer(delegate), TagContainer {

    var joinedGame: Any? by nullable
    override var tags: CopyOnWriteArraySet<String> by default { CopyOnWriteArraySet<String>() }

}