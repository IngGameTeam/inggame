package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface PartyAlert : Wrapper {

    val PARTY_DISBANDED: Alert
    val PARTY_DISBAND_IS_LEADER_ONLY: Alert
    val LEFT_PARTY: Alert

}

class PartyAlertImp(wrapper: Wrapper) : Wrapper by wrapper, PartyAlert {
    override val PARTY_DISBANDED: Alert by nonNull
    override val PARTY_DISBAND_IS_LEADER_ONLY: Alert by nonNull
    override val LEFT_PARTY: Alert by nonNull

}