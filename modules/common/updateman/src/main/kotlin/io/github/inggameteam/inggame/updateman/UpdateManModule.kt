package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class UpdateManModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onUpdateMan(event: ComponentLoadEvent) {
        event.registerClass {
            classOf(::UpdateHelper)
            classOf(::UpdateWatchDogHelper)
            classOf(UpdateSettings::class)
        }
        event.componentServiceDSL.apply {
            cs("update", type = ComponentServiceType.RESOURCE, isSavable = true).cs("handler")
        }
        event.addModule(newModule("update", ::UpdateRepo))
    }

}