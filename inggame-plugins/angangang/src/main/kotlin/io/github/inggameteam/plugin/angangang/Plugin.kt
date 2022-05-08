package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.base.Hub
import io.github.inggameteam.minigame.ui.MinigameCommand

class Plugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_minigame",
    width = 300, height = 128,
    init = arrayOf(::TestGame, ::Hub)
) {

    override fun onEnable() {
        super.onEnable()
        MinigameCommand(this)
    }
}
