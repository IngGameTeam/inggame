package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.reflections.Reflections

class GameModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.addModule(module(createdAtStart = true) {
            singleOf(::JoinHubOnJoinServer)
            singleOf(::PrintOnMove)
            singleOf(::GameHelper)
            singleOf(::SectionalHelper)
            singleOf(::SectionalHandler)
            singleOf(::GameInstanceService)
            singleOf(::SectorLoader)
            singleOf(::HubLoader)
            singleOf(::SpawnPlayerHandler)
            singleOf(::SpawnOnJoin)
        })
        event.registerClass(
            SpawnOnJoin::class,
            PrintOnMove::class,
            GameHelper::class,
            JoinHubOnJoinServer::class,
            SectorLoader::class,
            GameServer::class,
            Game::class,
            GameAlert::class,
            GPlayer::class,
            SectionalHandler::class,
            Sectional::class,
            SpawnPlayerHandler::class,
            SpawnPlayer::class
        )
        event.addModule(newModule("game-player", ::GamePlayerService))
        event.addModule(newModule("game-instance", ::GameInstanceRepository))
        event.addModule(newModule("custom-game", ::CustomGameService))
        event.register {
            "game-player" isLayer true cs "game-instance" isLayer true cs "custom-game" isSavable true cs "game-resource" isSavable true cs "default"
        }
        event.addModule(createSingleton(::GameServer, "server", "singleton"))
    }
}
