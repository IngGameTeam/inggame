package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.hub.HubLoader
import io.github.inggameteam.inggame.minigame.base.hub.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.*
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnOnJoin
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayer
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerHandler
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerImp
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeath
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathImp
import io.github.inggameteam.inggame.minigame.component.CustomGameService
import io.github.inggameteam.inggame.minigame.component.GameInstanceRepository
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin

class GameModule(plugin: Plugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.registerClass {
            classOf(::SpawnOnJoin)
            classOf(::JoinHubOnJoinServer)
            classOf(::GameHelper)
            classOf(::SectionalHelper)
            classOf(::SectionalHandler)
            classOf(::GameInstanceService)
            classOf(::SectorLoader)
            classOf(::HubLoader)
            classOf(::SpawnPlayerHandler)
            classOf(::SpawnOnJoin)
            classOf(::GameServer)
            classOf(::GPlayer)
            classOf(::SectionalHandler)
            classOf(::VoidDeathHandler)
            classOf(
                Game::class,
                GameAlert::class,
                Sectional::class,
                SpawnPlayer::class,
                VoidDeath::class,
            )
        }
        event.addModule(newModule("game-player", ::GamePlayerService))
        event.addModule(newModule("game-instance", ::GameInstanceRepository))
        event.addModule(newModule("custom-game", ::CustomGameService))
        event.register {
            "game-player" isLayer true cs "game-instance" isLayer true cs "custom-game" isSavable true cs "game-resource" isSavable true cs "default"
        }
        event.addModule(createSingleton(::GameServer, "server", "singleton"))
    }
}
