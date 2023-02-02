package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.handler.*
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameAlert
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class GameModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.addModule(module(createdAtStart = true) {
            singleOf(::JoinHubOnJoinServer)
            singleOf(::PrintOnMove)
            singleOf(::GameHelper)
            singleOf(::SectionalHelper)
            singleOf(::Sectional)
            singleOf(::GameInstanceService)
            singleOf(::SectorLoader)
            singleOf(::HubLoader)
            singleOf(::SpawnPlayer)
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
            Sectional::class,
            SpawnPlayer::class,
            io.github.inggameteam.inggame.minigame.wrapper.game.Sectional::class,
            SpawnPlayer::class,
            io.github.inggameteam.inggame.minigame.wrapper.game.SpawnPlayer::class
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
