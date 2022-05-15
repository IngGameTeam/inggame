package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.angangang.game.base.CompetitionImpl

@Deprecated("unused")
class Duel(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame {
    override val name get() = "duel"
}