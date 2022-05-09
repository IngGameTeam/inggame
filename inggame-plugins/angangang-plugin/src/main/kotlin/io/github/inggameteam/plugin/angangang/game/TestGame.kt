package io.github.inggameteam.plugin.angangang.game

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.angangang.LeaveWhenYouClickLeaveItem
import io.github.inggameteam.minigame.base.SpawnOnStart
import io.github.inggameteam.minigame.base.competition.SpawnTeamPlayer
import io.github.inggameteam.minigame.base.competition.TeamCompetition

class TestGame(plugin: GamePlugin, point: Sector) : TeamCompetition(plugin, point),
    SpawnOnStart, SpawnTeamPlayer, LeaveWhenYouClickLeaveItem
{
    override val name get() = "team-game"

}
