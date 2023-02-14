package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
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
        event.addModule(newModule("root", ::PlayerService))
        event.componentServiceDSL
            .cs("player-instance", type = ComponentServiceType.MASK, isSavable = true)
        event.addModule(newModule("player-instance", ::PlayerInstanceService))
    }

}