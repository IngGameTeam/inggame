package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.player.handler.PlayerLanguage
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.dsl.module

class PlayerModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.addModule(module(createdAtStart = true) {
            single { PlayerLoader(get(), get()) }
            single { PlayerLanguage(get(), get()) }
        })
        event.addModule(newModule("root", ::PlayerService))
        event.register { "player-instance" isLayer true }
        event.addModule(newModule("player-instance", ::PlayerInstanceService))
    }

}