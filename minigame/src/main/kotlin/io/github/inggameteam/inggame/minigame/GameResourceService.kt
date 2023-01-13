package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

typealias GameName = String

class GameResourceService(componentService: ComponentService, plugin: IngGamePlugin)
    : ResourceComponentService by componentService as ResourceComponentService
{
/*
player layer
game instance layer
player-game layer
game resource
 */
}