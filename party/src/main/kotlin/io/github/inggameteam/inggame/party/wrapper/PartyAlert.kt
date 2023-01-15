package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.model.Alert

interface PartyAlert : Delegate {

    val PARTY_DISBANDED: Alert

}