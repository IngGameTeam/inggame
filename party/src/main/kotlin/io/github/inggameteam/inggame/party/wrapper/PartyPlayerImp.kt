package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Wrapper

class PartyPlayerImp(wrapper: Wrapper) : PartyPlayer, Wrapper by wrapper {
    override val joinedParty: Party by nonNull


}