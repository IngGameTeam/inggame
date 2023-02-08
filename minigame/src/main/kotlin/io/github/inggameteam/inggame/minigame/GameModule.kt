package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentServiceRegisterEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.minigame.base.death.DeathHandler
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.gameserver.*
import io.github.inggameteam.inggame.minigame.base.gameserver.hub.HubLoader
import io.github.inggameteam.inggame.minigame.base.gameserver.hub.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.base.locational.Locational
import io.github.inggameteam.inggame.minigame.base.locational.LocationalImp
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.*
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnOnJoin
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnOnStart
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayer
import io.github.inggameteam.inggame.minigame.base.spawnplayer.SpawnPlayerHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeath
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHelper
import io.github.inggameteam.inggame.minigame.component.CustomGameService
import io.github.inggameteam.inggame.minigame.component.GameInstanceRepository
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class GameModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentServiceRegisterEvent) {
        event.registerClass {
            classOf(::SpawnOnJoin)
            classOf(::JoinHubOnJoinServer)
            classOf(::QuitGameOnQuitServer)
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
            classOf(::SpawnOnStart)
            classOf(::AnnounceGameBegin)
            classOf(::LocationalImp)
            classOf(::SpawnOnStart)
            classOf(::ParticleOnGameBegin)
            classOf(::StartPlayersAmountAlert)
            classOf(::ClearSectorOnDisableServer)
            classOf(::GameHandler)
            classOf(
                Game::class,
                GameAlert::class,
                Locational::class,
                Sectional::class,
                SpawnPlayer::class,
                VoidDeath::class,
            )
        }
        event.addModule(newModule("game-player", ::GamePlayerService))
        event.addModule(newModule("game-instance", ::GameInstanceRepository))
        event.addModule(newModule("custom-game", ::CustomGameService))
        event.register {
            this
                .cs("game-player", isMask = true)
                .cs("game-instance", isMask = true)
                .cs("custom-game", isLayer = true, isSavable = true)
                .cs("game-resource", isMulti = true, key = "game-language", root = "player-instance")
                .apply {
                    this
                        .cs("game-template-korean", isSavable = true)
                        .cs("game-abstract-korean", isSavable = true)
                        .cs("handler")
                }
         }
        event.addModule(createSingleton(::GameServer, "server", "singleton"))
    }
}
