package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.TeamCompetition

class TeamWars(plugin: GamePlugin) : TeamCompetition(plugin), SimpleGame {
    override val name get() = "team-wars"
}