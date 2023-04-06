package io.github.inggameteam.inggame.component.view.component

import io.github.inggameteam.inggame.component.Custom
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Custom("view", save = true)
class ViewCustom(componentService: ComponentService) : ComponentService by componentService