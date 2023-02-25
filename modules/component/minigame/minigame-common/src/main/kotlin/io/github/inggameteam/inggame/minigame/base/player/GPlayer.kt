package io.github.inggameteam.inggame.minigame.base.player

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.player.container.ContainerElement
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayerImp
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import io.github.inggameteam.inggame.utils.TagContainer

class GPlayer(wrapper: Wrapper) : WrappedPlayerImp(wrapper), TagContainer, ContainerElement<Game> {

    var joinedGame: Game by nonNull
    override var isPlaying: Boolean by nonNull
    override var joinedContainer: Game by nonNull
    override var tags: SafeSetWithToString<String> by default { SafeSetWithToString<String>() }

}