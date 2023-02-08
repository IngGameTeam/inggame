package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.wrapper.Wrapper

class PartyAlertImp(wrapper: Wrapper) : Wrapper by wrapper, PartyAlert {
    override val PARTY_DISBANDED: Alert by nonNull

}