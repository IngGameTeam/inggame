package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Wrapper

interface PartyPlayer : Wrapper {

    val joinedParty: Party
}