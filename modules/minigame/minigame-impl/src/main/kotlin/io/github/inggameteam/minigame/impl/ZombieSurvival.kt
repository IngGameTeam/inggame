package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.BarGame
import io.github.inggameteam.minigame.base.InfectionImpl
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.base.SpawnTeamPlayer
import io.github.inggameteam.player.hasTags
import org.bukkit.boss.BarColor

class ZombieSurvival(plugin: GamePlugin) : InfectionImpl(plugin), SimpleGame, SpawnTeamPlayer, BarGame {
    override val name get() = "zombie-survival"
    override val bar by lazy { GBar(plugin, size = 750.0, reversed = true) }
    override fun beginGame() {
        super.beginGame()
        bar.update("생존자 비상 탈출", color = BarColor.PURPLE)
        gameTask = bar.startTimer {
                joined.hasTags(PTag.PLAY, PTag.RED).forEach {
                    it.removeTag(PTag.PLAY)
                }
                stopCheck()
        }

    }
}