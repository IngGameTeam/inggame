package io.github.inggameteam.inggame.inggame

import io.github.inggameteam.inggame.component.ComponentModule
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.loader.ComponentLoader
import io.github.inggameteam.inggame.component.loader.loadComponents
import io.github.inggameteam.inggame.component.view.ComponentViewModule
import io.github.inggameteam.inggame.item.ItemModule
import io.github.inggameteam.inggame.player.PlayerModule
import io.github.inggameteam.inggame.updateman.UpdateManModule
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.Bukkit
import org.koin.core.Koin
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class IngGameMain {

    private fun loadApp(plugin: IngGamePlugin): Koin {
        return plugin.run {
            ComponentModule(this)
            ItemModule(this)
            ComponentViewModule(this)
            UpdateManModule(this)
            PlayerModule(this)
            koinApplication {
                modules(module { single { plugin } bind IngGamePlugin::class })
                listOfNotNull(
                    loadComponents(plugin),
                ).apply { modules(this) }
            }.koin
        }.also { app ->
            app.get<ComponentLoader>()
            app.getAll<ComponentService>().map(ComponentService::layerPriority)
            Bukkit.getPluginManager().callEvent(IngGamePluginEnableEvent(plugin))
        }
    }

    private var appSemaphore = false
    private var _app: Koin? = null
    val app: Koin
        get() = run {
            if (appSemaphore) throw AssertionError("an error occurred while get app while initializing app")
            appSemaphore = true
            if (_app === null)_app = run { loadApp(
                Bukkit.getPluginManager().plugins.filterIsInstance<IngGamePlugin>().firstOrNull()
                    ?: throw AssertionError("an app loading error occurred due to IngGamePlugin is not loaded")
            ) }
            appSemaphore = false
            _app!!
        }

    fun isLoaded() = _app !== null

    fun closeApp() {
        _app?.close()
        _app = null
    }

}