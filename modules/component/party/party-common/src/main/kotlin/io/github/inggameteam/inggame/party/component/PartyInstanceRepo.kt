package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

class PartyInstanceRepo(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService