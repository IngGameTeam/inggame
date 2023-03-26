package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Layered
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Layered("game")
class CustomGameService(componentService: ComponentService) : ComponentService by componentService
