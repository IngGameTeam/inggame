package io.github.inggameteam.inggame.party

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

class PartyRequestService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
