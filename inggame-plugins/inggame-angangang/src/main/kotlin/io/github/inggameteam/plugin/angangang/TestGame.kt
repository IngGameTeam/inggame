package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.base.SimpleSectional

class TestGame(plugin: GamePlugin, point: Sector) : SimpleSectional(plugin, point) {

    override val name get() = "test-game"
}
