package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayerImp

interface PartyPlayer : Wrapper, WrappedPlayer {

    val joinedParty: Party
}

class PartyPlayerImp(wrapper: Wrapper) : PartyPlayer, WrappedPlayerImp(wrapper) {
    override val joinedParty: Party by nonNull


}