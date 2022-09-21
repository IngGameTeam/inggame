package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnTeamPlayer
import io.github.inggameteam.minigame.base.TeamCompetitionImpl

class TeamWars(plugin: GamePlugin) : TeamCompetitionImpl(plugin), SimpleGame, SpawnTeamPlayer {
    override val name get() = "team-wars"
}