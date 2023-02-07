package io.github.inggameteam.inggame.minigame.base.player

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer
import io.github.inggameteam.inggame.utils.TagContainer
import java.util.concurrent.CopyOnWriteArraySet

class GPlayer(wrapper: Wrapper) : WrappedPlayer(wrapper), TagContainer {

    var joinedGame: GameImp by nonNull
    override var tags: CopyOnWriteArraySet<String> by default { CopyOnWriteArraySet<String>() }

}