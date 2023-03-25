package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.party.wrapper.Party
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.player.container.ContainerHelper
import io.github.inggameteam.inggame.player.container.ContainerHelperImp
import lombok.experimental.Helper

@Helper
class PartyInstanceService(partyInstanceRepo: PartyInstanceRepo, partyPlayerService: PartyPlayerService)
    : ComponentService by partyInstanceRepo,
    ContainerHelper<Party, PartyPlayer> by ContainerHelperImp(partyInstanceRepo, partyPlayerService)
