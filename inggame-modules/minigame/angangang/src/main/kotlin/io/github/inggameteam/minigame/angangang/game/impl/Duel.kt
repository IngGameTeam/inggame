package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.CompetitionImpl

class Duel(plugin: GamePlugin, point: Sector) : CompetitionImpl(plugin, point), SimpleGame {
    override val name get() = "duel"
}