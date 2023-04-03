package io.github.inggameteam.inggame.component.view.component

import io.github.inggameteam.inggame.component.Masked
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService

@Masked("view", save = false)
class ViewPlayer(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService