package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.TeamCompetition
import io.github.inggameteam.minigame.base.TeamCompetitionImpl

class TeamWars(plugin: GamePlugin) : TeamCompetitionImpl(plugin), SimpleGame {
    override val name get() = "team-wars"
}