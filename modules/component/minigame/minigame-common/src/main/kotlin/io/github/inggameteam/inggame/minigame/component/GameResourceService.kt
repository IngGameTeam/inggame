package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Multi
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Multi("game")
class GameResourceService(componentService: ComponentService)
    : ComponentService by componentService