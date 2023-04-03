package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.component.Resource
import io.github.inggameteam.inggame.component.componentservice.ComponentService

@Resource("update")
class UpdateRepo(componentService: ComponentService) : ComponentService by componentService