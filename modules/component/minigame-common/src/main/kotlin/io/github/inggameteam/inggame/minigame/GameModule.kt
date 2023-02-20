package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.createSingleton
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.minigame.base.*
import io.github.inggameteam.inggame.minigame.base.competition.Competition
import io.github.inggameteam.inggame.minigame.base.competition.SoloCompetitionHandler
import io.github.inggameteam.inggame.minigame.base.death.DeathHandler
import io.github.inggameteam.inggame.minigame.base.game.*
import io.github.inggameteam.inggame.minigame.base.gameserver.*
import io.github.inggameteam.inggame.minigame.base.gameserver.hub.HubLoader
import io.github.inggameteam.inggame.minigame.base.gameserver.hub.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.base.locational.Locational
import io.github.inggameteam.inggame.minigame.base.locational.LocationalImp
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.*
import io.github.inggameteam.inggame.minigame.base.spawnplayer.*
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeath
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHandler
import io.github.inggameteam.inggame.minigame.base.voiddeath.VoidDeathHelper
import io.github.inggameteam.inggame.minigame.component.*
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class GameModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onRegisterComponentService(event: ComponentLoadEvent) {
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
            classOf(::TeleportOnSpawn)
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
            classOf(::ParticleOnGameBegin)
            classOf(::StartPlayersAmountAlert)
            classOf(::GameHandler)
            classOf(::SetGameModeOnSpawnHandler)
            classOf(::KitOnSpawnHandler)
            classOf(::SoloCompetitionHandler)
            classOf(
                Competition::class,
                KitOnSpawn::class,
                Game::class,
                GameAlert::class,
                Locational::class,
                Sectional::class,
                VoidDeath::class,
            )
        }
        event.addModule(newModule("game-player", ::GamePlayerService))
        event.addModule(newModule("game-instance", ::GameInstanceRepository))
        event.addModule(newModule("custom-game", ::CustomGameService))
        event.addModule(newModule("custom-game", ::CustomGameService))
        event.addModule(newModule("game-resource", ::GameResourceService))
        event.componentServiceDSL
                .cs("game-player", type = ComponentServiceType.MASKED)
                .cs("game-instance", type = ComponentServiceType.MASKED)
                .cs("custom-game", type = ComponentServiceType.LAYER, isSavable = true)
                .cs("game-resource", type = ComponentServiceType.MULTI, key = "game-language", root = "player-instance")
                .apply {
                    this
                        .cs("game-template-korean", isSavable = true)
                        .cs("game-abstract-korean", isSavable = true)
                        .cs("handler")
                }
        event.addModule(createSingleton(::GameServer, "server", "singleton"))
    }
}
