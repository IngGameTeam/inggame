package io.github.inggameteam.inggame.component.view.handler

import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.helper.LayeredPlayerLoader
import io.github.inggameteam.inggame.component.view.component.ViewPlayer
import io.github.inggameteam.inggame.utils.IngGamePlugin

class ViewPlayerLoader(componentService: ViewPlayer, plugin: IngGamePlugin) :
    LayeredPlayerLoader(componentService as LayeredComponentService, plugin)
