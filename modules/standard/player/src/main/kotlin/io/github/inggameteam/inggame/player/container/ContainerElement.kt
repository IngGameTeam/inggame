package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayerImp

interface ContainerElement<CONTAINER: Wrapper> : WrappedPlayer {
    var isPlaying: Boolean
    var joinedContainer: CONTAINER
}

class ContainerElementImp(wrapper: Wrapper) : ContainerElement<Container<ContainerElement<*>>>, WrappedPlayerImp(wrapper) {
    override var isPlaying: Boolean by nonNull
    override var joinedContainer: Container<ContainerElement<*>> by nonNull
}