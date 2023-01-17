package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.minigame.handler.GameHelper
import io.github.inggameteam.inggame.minigame.handler.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.handler.PrintOnMove
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.game.GameAlert
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.mongodb.ModelRegistry
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun registerGameModels() = module(createdAtStart = true) {
    factory {
        ModelRegistry(
            GameServer::class,
            Game::class,
            GameAlert::class,
            GPlayer::class,
        )
    }
}

fun createGameService(name: String) = module {
    single { GameInstanceService(get(), get(), get(), get(named(name))) }
}

fun createGameResource(name: String) = module {
    single { GameResourceService(get(named(name))) }
}

fun createGamePlayerService(name: String) = module {
    single { GamePlayerService(get(named(name))) }
}

fun createGameHandlers() = module {
    singleOf(::JoinHubOnJoinServer)
    singleOf(::PrintOnMove)
    singleOf(::GameHelper)
}