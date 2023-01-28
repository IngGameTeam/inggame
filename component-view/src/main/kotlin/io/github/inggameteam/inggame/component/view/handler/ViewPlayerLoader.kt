package io.github.inggameteam.inggame.component.view.handler

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.helper.LayeredPlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin

class ViewPlayerLoader(componentService: ComponentService, plugin: IngGamePlugin) :
    LayeredPlayerLoader(componentService as LayeredComponentService, plugin) {
}