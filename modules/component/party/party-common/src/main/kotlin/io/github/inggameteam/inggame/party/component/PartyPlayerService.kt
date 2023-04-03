package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.Masked
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

@Masked("party", save = false)
class PartyPlayerService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
