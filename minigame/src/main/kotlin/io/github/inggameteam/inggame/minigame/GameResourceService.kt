package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService

class GameResourceService(componentService: ComponentService)
    : ResourceComponentService by componentService as ResourceComponentService
{

}