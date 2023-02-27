package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer

interface ContainerElement<CONTAINER: Wrapper> : WrappedPlayer {
    var isPlaying: Boolean
    var joined: CONTAINER
}

