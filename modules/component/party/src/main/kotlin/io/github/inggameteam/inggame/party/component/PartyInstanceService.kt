package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.player.container.ContainerHelper
import io.github.inggameteam.inggame.player.container.ContainerHelperImp

class PartyInstanceService(
    partyInstanceRepo: PartyInstanceRepo,
    partyPlayerService: PartyPlayerService
) : ContainerHelper<Party, PartyPlayer> by ContainerHelperImp(
    partyInstanceRepo, partyPlayerService,
    PartyPlayer::joinedParty, Party::partyJoined)
{



}