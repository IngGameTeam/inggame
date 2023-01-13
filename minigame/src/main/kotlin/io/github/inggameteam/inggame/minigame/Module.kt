package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.minigame.handler.JoinHubOnJoinServer
import io.github.inggameteam.inggame.minigame.handler.PrintOnMove
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun createGameService(name: String) = module {
    single { GameInstanceService(get(), get(), get(named(name))) }
}

fun createGameResourceService(name: String) = module {
    single { GameResourceService(get(named(name)), get()) }
}

fun createPlayerGameService(name: String) = module {
    single { GameResourceService(get(named(name)), get()) }
}

fun createGameHandlers() = module(createdAtStart = true) {
    singleOf(::JoinHubOnJoinServer)
    singleOf(::PrintOnMove)
}