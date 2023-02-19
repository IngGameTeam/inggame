package io.github.inggameteam.inggame.party.wrapper

import io.github.inggameteam.inggame.player.warpper.WrappedPlayer

interface PartyRequest {

    val partyRequestSender: WrappedPlayer
    val partyRequestReciver: WrappedPlayer
    val partyRequestedParty: Party
    val partyRequestCode: Int

}