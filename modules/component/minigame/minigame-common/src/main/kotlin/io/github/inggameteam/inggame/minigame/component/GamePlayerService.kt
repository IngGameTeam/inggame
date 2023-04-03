package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Masked
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

@Masked("game", save = false)
class GamePlayerService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
