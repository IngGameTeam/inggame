package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin

fun loadComponentService(plugin: IngGamePlugin) = ComponentServiceRegisterEvent()
        .apply { plugin.server.pluginManager.callEvent(this) }
        .getNewModule()

