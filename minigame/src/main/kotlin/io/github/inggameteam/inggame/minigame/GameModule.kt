package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.hub.HubLoader
import io.github.inggameteam.inggame.minigame.base.hub.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.*
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnOnJoin
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayer
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerHandler
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerImp
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
            classOf(::GameImp)
            classOf(::GameAlertImp)
            classOf(::GPlayer)
            classOf(::SectionalHandler)
            classOf(::SectionalImp)
            classOf(::SpawnPlayerImp)
            classOf(
                Game::class,
                GameAlert::class,
                Sectional::class,
                SpawnPlayer::class,

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
