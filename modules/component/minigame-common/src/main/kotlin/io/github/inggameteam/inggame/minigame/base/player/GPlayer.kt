package io.github.inggameteam.inggame.minigame.base.player

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import io.github.inggameteam.inggame.utils.TagContainer
import java.util.concurrent.CopyOnWriteArraySet

class GPlayer(wrapper: Wrapper) : WrappedPlayer(wrapper), TagContainer {

    var joinedGame: Game by nonNull
    override var tags: SafeSetWithToString<String> by default { SafeSetWithToString<String>() }

}