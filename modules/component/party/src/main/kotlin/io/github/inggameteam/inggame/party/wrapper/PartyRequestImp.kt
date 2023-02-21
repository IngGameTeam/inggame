package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer

class PartyRequestImp(wrapper: Wrapper) : Wrapper by wrapper, PartyRequest {
    override val partyRequestSender: WrappedPlayer by nonNull
    override val partyRequestReciver: WrappedPlayer by nonNull
    override val partyRequestedParty: Party by nonNull
    override val partyRequestCode: Int by nonNull
}