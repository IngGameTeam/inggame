package io.github.inggameteam.inggame.party.handler

import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.component.PartyRequestInstanceRepo
import io.github.inggameteam.inggame.party.wrapper.*
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.fastForEach

class PartyRequestHelper(
    val plugin: IngGamePlugin,
    val partyHelper: PartyHelper,
    val partyPlayerService: PartyPlayerService,
    val partyRequestInstanceRepo: PartyRequestInstanceRepo,
    val partyServer: PartyServer,
) {

    fun acceptInvitation(dispatcher: PartyPlayer, inviteCode: Int) {
        val requests = partyRequestInstanceRepo.getAvailableParties()
            .filter { it.partyRequestReciver == dispatcher && inviteCode == it.partyRequestCode }.toList()
        if (requests.isEmpty()) {
            dispatcher[::PartyAlertImp].NO_PARTY_INVITATION.send(dispatcher)
        } else {
            val partyRequestedParty = requests.last().partyRequestedParty
            partyRequestInstanceRepo.removeIf { requests.contains(it) }
            partyHelper.joinContainer(partyRequestedParty, dispatcher)
        }
    }


    fun inviteAll(sender: PartyPlayer) {
        if (sender.joined == partyServer.defaultParty) {
            val party = sender.joined
            partyPlayerService.getAll(::PartyPlayerImp).filter {
                sender != it && !party.joinedPlayers.contains(it) && !party.partyBanList.contains(it.uniqueId)
            }.forEach{ receiver ->
                val partyRequest = partyRequestInstanceRepo.createNewPartyRequest(sender, receiver)
                receiver[::PartyAlertImp].PARTY_REQUEST_TO_ALL.send(receiver, sender, party, partyRequest.partyRequestCode)
            }
            sender[::PartyAlertImp].SENT_PARTY_REQUEST_TO_ALL.send(sender, party)
            party.joinedPlayers.filter { it != sender }.fastForEach { p ->
                p[::PartyAlertImp].SENT_PARTY_REQUEST_TO_ALL_RECEIVE_ALL.send(p, sender, party)
            }
        }
    }

    fun invitePlayer(sender: PartyPlayer, receiver: PartyPlayer) {
        if (sender.joined != partyServer.defaultParty) {
            val party = sender.joined
            if (party.partyBanList.contains(receiver.uniqueId)) {
                sender[::PartyAlertImp].CANNOT_REQUEST_PARTY_DUE_TO_BANNED.send(sender, receiver, party)
                return
            }
            val partyRequest = partyRequestInstanceRepo.createNewPartyRequest(sender, receiver)
            receiver[::PartyAlertImp].PARTY_REQUEST.send(receiver, sender, party, partyRequest.partyRequestCode)
            sender[::PartyAlertImp].SENT_PARTY_REQUEST.send(sender, receiver, party)
            party.joinedPlayers.filter { it != sender }.fastForEach { p ->
                p[::PartyAlertImp].SENT_PARTY_REQUEST_RECEIVE_ALL.send(p, sender, receiver, party)
            }
        }
    }



}