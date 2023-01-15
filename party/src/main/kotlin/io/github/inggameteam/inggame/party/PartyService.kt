package io.github.inggameteam.inggame.party

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer

class PartyService(
    componentService: ComponentService,
    partyPlayerService: PartyPlayerService
) : ContainerComponentService by ContainerComponentServiceImp(
    componentService as LayeredComponentService, partyPlayerService,
    PartyPlayer::joinedParty.name, Party::partyJoined.name)
{



}