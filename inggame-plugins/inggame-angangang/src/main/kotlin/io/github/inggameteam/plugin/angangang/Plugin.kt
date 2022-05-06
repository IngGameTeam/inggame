package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.base.Hub

class Plugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_minigame",
    width = 300, height = 128,
    init = arrayOf(::TestGame, ::Hub)
) {


}