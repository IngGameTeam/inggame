package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Resource
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Resource("game")
class GameResourceService(componentService: ComponentService)
    : ComponentService by componentService