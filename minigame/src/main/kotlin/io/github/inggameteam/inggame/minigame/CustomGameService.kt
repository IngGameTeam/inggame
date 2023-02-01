package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

class CustomGameService(componentService: ComponentService) : ComponentService by componentService
