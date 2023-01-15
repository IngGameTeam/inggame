package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class GamePlayerService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
{



}