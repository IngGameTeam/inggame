package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class GameResourceService(componentService: ComponentService, plugin: IngGamePlugin)
    : ResourceComponentService by componentService as ResourceComponentService
