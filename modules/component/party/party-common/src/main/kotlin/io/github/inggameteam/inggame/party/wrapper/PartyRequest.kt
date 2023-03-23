package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.player.wrapper.WrappedPlayer

interface PartyRequest : Wrapper {

    var partyRequestSender: WrappedPlayer
    var partyRequestReciver: WrappedPlayer
    var partyRequestedParty: Party
    var partyRequestCode: Int
    var isInitialized: Boolean

}

class PartyRequestImp(wrapper: Wrapper) : Wrapper by wrapper, PartyRequest {
    override var partyRequestSender: WrappedPlayer by nonNull
    override var partyRequestReciver: WrappedPlayer by nonNull
    override var partyRequestedParty: Party by nonNull
    override var partyRequestCode: Int by nonNull
    override var isInitialized: Boolean by nonNull
}