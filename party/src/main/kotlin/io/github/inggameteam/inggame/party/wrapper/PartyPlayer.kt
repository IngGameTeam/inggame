package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate

interface PartyPlayer : Delegate {

    val joinedParty: Party
}