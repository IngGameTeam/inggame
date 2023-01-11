package io.github.inggameteam.inggame.minigame

import org.koin.core.qualifier.named
import org.koin.dsl.module

fun createGameService(name: String) = module {
    single { GameService(get(), get(), get(named(name))) }
}