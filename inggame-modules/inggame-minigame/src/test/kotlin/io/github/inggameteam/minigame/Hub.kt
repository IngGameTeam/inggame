package io.github.inggameteam.minigame

import io.github.inggameteam.utils.IntVector

class Hub(plugin: GamePlugin, point: IntVector) : GameImpl(plugin, point) {
    override val name get() = plugin.gameRegister.hubName
}