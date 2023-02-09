package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.inggame.component.loader.loadComponents
import io.github.inggameteam.inggame.mongodb.loadMongoModule
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun loadApp(plugin: IngGamePlugin): Koin {
    return plugin.run {
        koinApplication {
            modules(module { single { plugin } bind IngGamePlugin::class })
            listOfNotNull(
                loadComponents(),
                loadMongoModule(plugin),
            ).apply { modules(this) }
        }.koin
    }
}