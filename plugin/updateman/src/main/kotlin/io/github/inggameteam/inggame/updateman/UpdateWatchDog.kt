package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.runNow
import kotlin.concurrent.thread

class UpdateWatchDog(
    private val settings: UpdateSettings,
    private val updateHelper: UpdateHelper,
    private val plugin: IngGamePlugin
) {

    init {
        var function: () -> Unit = {}
        function = {
            thread {
                update()
                function.runNow(plugin)
            }
        }
        function.runNow(plugin)
    }

    private fun update() {
        updateHelper.apply {
            if (updateGit(settings)) {
                plugin.logger.info("Updating...")
                runBuild(settings)
                plugin.logger.info("Update Done!")
                deploy(settings)
            }
        }
    }


}