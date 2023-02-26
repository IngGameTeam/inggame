package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.container.ContainerElement
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayerImp

interface PartyPlayer : Wrapper, WrappedPlayer, ContainerElement<Party> {
    override var joinedContainer: Party
}

class PartyPlayerImp(wrapper: Wrapper) : PartyPlayer, ContainerElement<Party>, WrappedPlayerImp(wrapper) {
    override var isPlaying: Boolean
        get() = true
        set(value) {}
    override var joinedContainer: Party by nonNull


}