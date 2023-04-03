package io.github.inggameteam.inggame.item.component

import io.github.inggameteam.inggame.component.Resource
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Resource("item")
class ItemResource(componentService: ComponentService)
    : ComponentService by componentService