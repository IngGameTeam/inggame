package io.github.inggameteam.inggame.item.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentService

class ItemResource(componentService: ComponentService)
    : ResourceComponentService by componentService as ResourceComponentService