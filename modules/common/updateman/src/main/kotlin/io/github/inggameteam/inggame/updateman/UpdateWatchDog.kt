package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.utils.runNow
import kotlin.concurrent.thread

class UpdateWatchDog(
    private val settings: UpdateSettings,
    private val updateHelper: UpdateHelper,
) {


    init {
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

    private fun update(): Unit = updateHelper.run {
        if (updateGit(settings)) {
            settings.plugin.logger.info("Updating...")
            runBuild(settings)
            settings.plugin.logger.info("Update Done!")
            deploy(settings)
        }
    }


}