package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.minigame.base.death.DeathHandler
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.hub.HubLoader
import io.github.inggameteam.inggame.minigame.base.hub.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.*
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnOnJoin
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayer
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeath
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHelper
import io.github.inggameteam.inggame.minigame.component.CustomGameService
import io.github.inggameteam.inggame.minigame.component.GameInstanceRepository
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.minigame.listener.ArrowStuckPrevent
import io.github.inggameteam.inggame.minigame.listener.DisableCollision
import io.github.inggameteam.inggame.minigame.listener.HideJoinLeaveMessage
import io.github.inggameteam.inggame.minigame.listener.NoUnderWaterFall
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

class GameModule(plugin: IngGamePlugin) : Listener(plugin) {

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
            classOf(::DeathHandler)
            classOf(::VoidDeathHelper)
            classOf(::NoHunger)
            classOf(::HideJoinLeaveMessage)
            classOf(::ArrowStuckPrevent)
            classOf(::DisableCollision)
            classOf(::EnableAttackSpeed)
            classOf(::NoUnderWaterFall)
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
            "game-player" isMask true cs "game-instance" isMask true cs "custom-game" isLayer true isSavable true cs "game-resource" isMulti true key "game-language" root "player-instance" csc {
                "game-resource-korean" isSavable true cs "default"
            }
        }
        event.addModule(createSingleton(::GameServer, "server", "singleton"))
    }
}
