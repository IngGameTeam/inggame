package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate

class PartyPlayerImp(delegate: Delegate) : PartyPlayer, Delegate by delegate {
    override val joinedParty: Party by nonNull


}