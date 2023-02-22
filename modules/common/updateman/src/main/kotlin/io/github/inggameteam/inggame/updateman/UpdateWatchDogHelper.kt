package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.runNow
import org.bukkit.event.EventHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import kotlin.concurrent.thread

class UpdateWatchDogHelper(
    private val updateHelper: UpdateHelper,
) : KoinComponent {

    private val settings get() = get<ComponentService>(named("component"))["update", ::UpdateSettingsImp]

    private fun assertStartWatchDog() = try {
        settings.apply {
            pluginName
            gitUrl
            branchName
            outputPath
            bashCmd
        }
        true
    } catch (_: Throwable) { false }

    @Suppress("unused")
    @EventHandler
    fun onEnable(event: IngGamePluginEnableEvent) {
        startWatchDog()
    }

    fun startWatchDog() {
        if (assertStartWatchDog()) {
            var function: () -> Unit = {}
            function = {
                thread {
                    val plugin = settings.plugin
                    if (plugin.isEnabled) {
                        update()
                        if (plugin.isEnabled) {
                            function.runNow(settings.plugin)
                        }
                    }
                }
            }
            function.runNow(settings.plugin)
        }
    }

    private fun update(): Unit = updateHelper.run {
        if (updateGit(settings)) {
            settings.plugin.logger.info("Updating...")
            runBuild(settings)
            settings.plugin.logger.info("Update Done!")
            deploy(settings)
        }
    }


}