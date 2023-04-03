package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import io.github.inggameteam.inggame.utils.runNow
import org.bukkit.event.EventHandler
import kotlin.concurrent.thread

@Helper
class UpdateWatchDog(
    private val updateHelper: UpdateHelper,
    private val updateRepo: UpdateRepo,
    plugin: IngGamePlugin,
) : HandleListener(plugin) {

    private val settings get() = updateRepo["update", ::UpdateSettingsImp]

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