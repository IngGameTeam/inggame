package io.github.inggameteam.inggame.party

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ContainerHelper
import io.github.inggameteam.inggame.component.componentservice.ContainerHelperImp
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer

class PartyService(
    componentService: ComponentService,
    partyPlayerService: PartyPlayerService
) : ContainerHelper by ContainerHelperImp(
    componentService as LayeredComponentService, partyPlayerService,
    PartyPlayer::joinedParty.name, Party::partyJoined.name)
{



}