package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.minigame.handler.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.handler.PrintOnMove
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun createGameService(name: String) = module {
    single { GameInstanceService(get(), get(), get(named(name))) }
}

fun createGameResource(name: String) = module {
    single { GameResourceService(get(named(name))) }
}

fun createGamePlayerService(name: String) = module {
    single { GamePlayerService(get(named(name))) }
}

fun createGameHandlers() = module(createdAtStart = true) {
    singleOf(::JoinHubOnJoinServer)
    singleOf(::PrintOnMove)
}