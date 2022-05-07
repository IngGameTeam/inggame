package io.github.inggameteam.plugin.minigame

import io.github.inggameteam.minigame.GameImpl
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector

class Hub(plugin: GamePlugin, point: Sector) : GameImpl(plugin, point) {
    override val name get() = plugin.gameRegister.hubName

}