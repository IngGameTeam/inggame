package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.component.event.IngGamePluginLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.player.handler.PlayerLanguage
import io.github.inggameteam.inggame.player.handler.PlayerLoader
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.dsl.module

class PlayerModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: IngGamePluginLoadEvent) {
        event.addModule(module(createdAtStart = true) {
            single { PlayerLoader(get(), get()) }
            single { PlayerLanguage(get(), get()) }
        })
        event.addModule(newModule("root", ::PlayerService))
//        event.register { "player-instance" isSavable true isMask true cs "default" }
        event.addModule(newModule("player-instance", ::PlayerInstanceService))
    }

}