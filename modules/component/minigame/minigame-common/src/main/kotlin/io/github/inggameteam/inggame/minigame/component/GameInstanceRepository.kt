package io.github.inggameteam.inggame.minigame.component

import io.github.inggameteam.inggame.component.Layered
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

@Layered("game", save = false)
class GameInstanceRepository(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
