package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.component.model.Alert

class PartyAlertImp(wrapper: Wrapper) : Wrapper by wrapper, PartyAlert {
    override val PARTY_DISBANDED: Alert by nonNull

}