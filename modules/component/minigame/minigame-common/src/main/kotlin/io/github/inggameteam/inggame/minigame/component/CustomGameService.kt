package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Custom
import io.github.inggameteam.inggame.component.Layered
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Custom("game")
class CustomGameService(componentService: ComponentService) : ComponentService by componentService
