package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.player.container.Container
import io.github.inggameteam.inggame.player.container.ContainerAlert
import io.github.inggameteam.inggame.player.container.ContainerElement
import io.github.inggameteam.inggame.player.handler.PlayerLanguage
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.dsl.module

class PlayerModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentLoadEvent) {
        event.addModule(module(createdAtStart = true) {
            single { PlayerLoader(get(), get()) }
            single { PlayerLanguage(get(), get()) }
        })
        event.registerClass {
            classOf(ContainerAlert::class)
            classOf(Container::class)
            classOf(ContainerElement::class)
        }
        event.addModule(newModule("root", ::PlayerService))
        event.componentServiceRegistry
            .cs("player-instance", type = ComponentServiceType.MASKED, isSavable = true)
        event.addModule(newModule("player-instance", ::PlayerInstanceService))
    }

}