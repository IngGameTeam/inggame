package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.boss.BarColor
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class HideAndSeek(plugin: GamePlugin) : TeamCompetitionImpl(plugin), SimpleGame
    NoItemDrop, NoItemPickup, BarGame, SpawnTeamPlayer, ScaleRedTeam
{
    override val name get() = "hide-and-seek"
    override val startPlayersAmount get() = 3
    override val bar = GBar(plugin)
    private var timeSize = comp.doubleOrNull("time-size")?:450.0
    var time = timeSize

    override fun randomizeTeam(redTeamSize: Int) {
        super<TeamCompetitionImpl>.randomizeTeam(redTeamSize)
        val jobDetective = joined.hasTags(PTag.BLUE, PTag.PLAY).random()
        playerData[jobDetective]!![JOB_DETECTIVE] = true
    }

    override fun spawn(player: GPlayer, spawn: String) {
        if (gameState === GameState.WAIT) {
            super.spawn(player, spawn)
            return
        }
        val playerJob = getPlayerJob(player)
        player.inventory.contents = comp.inventory("$playerJob", player.lang(plugin)).contents
//        player.player.teleport(getLocation("$playerJob"))
        comp.send(playerJob.toString(), player)
        if (player.hasTag(PTag.RED)) {
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 55555, 2))
        }
    }

    fun getPlayerJob(player: GPlayer): MurderGameJob? {
        return if (player.hasTag(PTag.RED)) MurderGameJob.MURDER
        else if (player.hasTag(PTag.BLUE)) {
            if (playerData[player]!![JOB_DETECTIVE] == null) MurderGameJob.CIVIL
            else MurderGameJob.DETECTIVE
        } else null
    }

    override fun beginGame() {
        super.beginGame()
        timeSize += joined.hasTags(PTag.PLAY).size * 30
        time = timeSize
        gameTask = ITask.repeat(plugin, 1, 1, {
            if (time <= 0) {
                joined.hasTags(PTag.PLAY, PTag.RED).forEach { it.damage(10000.0) }
            } else {
                time--
                bar.update(comp.string("bar-title", plugin.defaultLanguage), time/timeSize, BarColor.PURPLE)
            }
        })
    }

    enum class MurderGameJob {
        MURDER, CIVIL, DETECTIVE;
    }

    companion object {
        const val JOB_DETECTIVE = "jobDetective"
    }


}