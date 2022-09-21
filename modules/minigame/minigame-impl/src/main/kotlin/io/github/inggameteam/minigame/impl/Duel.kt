package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.SimpleGame

@Deprecated("unused")
class Duel(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame {
    override val name get() = "duel"
}