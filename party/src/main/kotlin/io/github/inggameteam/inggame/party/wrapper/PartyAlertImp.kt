package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.model.Alert

class PartyAlertImp(delegate: Delegate) : Delegate by delegate, PartyAlert {
    override val PARTY_DISBANDED: Alert by nonNull

}