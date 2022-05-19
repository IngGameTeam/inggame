package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.CompetitionImpl

@Deprecated("unused")
class Duel(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame {
    override val name get() = "duel"
}