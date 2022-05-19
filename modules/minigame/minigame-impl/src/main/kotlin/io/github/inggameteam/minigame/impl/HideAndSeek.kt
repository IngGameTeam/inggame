package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.hasTags

class HideAndSeek(plugin: GamePlugin) : TeamCompetition(plugin), NoItemDrop, NoItemPickup, BarGame, SpawnTeamPlayer {
    override val name get() = "hide-and-seek"
    override val startPlayersAmount get() = 3
    override val bar = GBar(plugin)
    private var timeSize = comp.doubleOrNull("time-size")?:450.0
    var time = timeSize
    var isVoting = false
    var lastVoted = 0L

    override fun randomizeTeam(redTeamSize: Int) {
        super.randomizeTeam(redTeamSize)
        val jobDetective = joined.hasTags(PTag.BLUE, PTag.PLAY).random()
        playerData[jobDetective]!![JOB_DETECTIVE] = true
    }

    enum class MurderGameJob {
        MURDER, CIVIL, DETECTIVE;
    }

    companion object {
        const val JOB_DETECTIVE = "jobDetective"
    }
    override fun generateHalfSize() = (joined.hasTags(PTag.PLAY).size/ 5.0).toInt().plus(1)


}