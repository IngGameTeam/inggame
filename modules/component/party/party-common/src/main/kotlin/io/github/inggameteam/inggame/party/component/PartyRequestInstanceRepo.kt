package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.Layered
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.party.wrapper.PartyRequest
import io.github.inggameteam.inggame.party.wrapper.PartyRequestImp
import io.github.inggameteam.inggame.utils.randomUUID

@Layered("party-request", save = false)
class PartyRequestInstanceRepo(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
{

    fun getAvailableParties(): List<PartyRequestImp> {
        return getAll(::PartyRequestImp).filter { it.isInitialized }
    }

    fun removeIf(predicate: (PartyRequest) -> Boolean) {
        getAll(::PartyRequestImp).filter(predicate).forEach { removeNameSpace(it.nameSpace) }
    }

    fun removePlayer(player: PartyPlayer) {
        removeIf { it.partyRequestReciver == player || it.partyRequestSender == player }
    }

    fun createNewPartyRequest(sender: PartyPlayer, reciver: PartyPlayer): PartyRequest {
        val nameSpace = randomUUID()
        addNameSpace(nameSpace)
        return get(nameSpace, ::PartyRequestImp).apply {
            partyRequestSender = sender
            partyRequestedParty = sender.joined
            partyRequestReciver = reciver
            partyRequestCode = Any().hashCode()
            isInitialized = true
        }
    }

}