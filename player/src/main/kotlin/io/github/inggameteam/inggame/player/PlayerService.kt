package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class PlayerService(componentService: ComponentService, plugin: IngGamePlugin)
    : LayeredComponentService by componentService as LayeredComponentService
{
    init { plugin.addSaveEvent { getAll().map(NameSpace::name).forEach { unload(it, true)} } }
}