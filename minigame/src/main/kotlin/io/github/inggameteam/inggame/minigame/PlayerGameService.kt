package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

class PlayerGameService(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
{

    

}