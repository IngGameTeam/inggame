package io.github.inggameteam.inggame.player

import io.github.inggameteam.inggame.player.handler.PlayerLoader
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun createPlayerModule(collection: String) = module {
    single { PlayerLoader(get(named(collection)), get()) }
}