package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface PartyServer : Wrapper {
    val defaultParty: Party
}

class PartyServerImp(wrapper: Wrapper) : PartyServer, SimpleWrapper(wrapper) {
    override val defaultParty: Party by nonNull
}