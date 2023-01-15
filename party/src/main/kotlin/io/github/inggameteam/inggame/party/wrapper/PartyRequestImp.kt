package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.player.warpper.WrappedPlayer

class PartyRequestImp(delegate: Delegate) : Delegate by delegate, PartyRequest {
    override val partyRequestSender: WrappedPlayer by nonNull
    override val partyRequestReciver: WrappedPlayer by nonNull
    override val party: Party by nonNull
    override val code: Int by nonNull
}