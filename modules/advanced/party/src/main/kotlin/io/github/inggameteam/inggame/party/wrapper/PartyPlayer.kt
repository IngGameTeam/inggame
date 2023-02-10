package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface PartyPlayer : Wrapper {

    val joinedParty: Party
}