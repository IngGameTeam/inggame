package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.Singleton
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface PartyServer : Wrapper {
    var defaultParty: Party
}

@Singleton("server", save = true)
class PartyServerImp(wrapper: Wrapper) : PartyServer, SimpleWrapper(wrapper) {
    override var defaultParty: Party by nonNull
}