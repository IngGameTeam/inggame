package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

class PlayerInstanceService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService