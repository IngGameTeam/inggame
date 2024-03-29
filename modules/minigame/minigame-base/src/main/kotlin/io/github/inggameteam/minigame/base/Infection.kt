package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.GameAlert.*
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.Infection.Companion.ORIGINAL_INFECTED
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasNoTags
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.runNow
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

interface Infection : ScaleRedTeam, SpawnPlayer {
    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onBeginInfection(event: GameBeginEvent) {
        if (event.game !== this) return
        joined.hasTags(PTag.RED).forEach { playerData[it]!![ORIGINAL_INFECTED] = true }
    }
    companion object {
        const val ORIGINAL_INFECTED = "originalInfected"
    }
}

abstract class InfectionImpl(plugin: GamePlugin) : TeamCompetitionImpl(plugin), Infection {


//    open fun updateBar() = bar.update("생존자 비상 탈출", color = BarColor.PURPLE)


    override fun calcWinner() {
        joined.hasTags(PTag.PLAY).hasNoTags(PTag.DEAD)
            .filter { playerData[it]!![ORIGINAL_INFECTED] == true }.apply {
                if (isEmpty()) super<Infection>.calcWinner()
                else {
                    comp.send(RED_TEAM_WIN, joined, joinToString(", "))
                    Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this@InfectionImpl, GPlayerList(this)))
                }
            }
    }

/*
    override fun spawn(player: GPlayer) {
        super.spawn(player)
        if (player.hasTag(PTag.RED)) {
            player.player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 55555, 2))
        }
    }
*/

    @EventHandler
    override fun competitionDeath(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val player = event.player
        if (player.hasTag(PTag.RED)) {
            addTask({ spawn(player) }.runNow(plugin))
            comp.send(RED_TEAM_DEATH, joined, player)
        } else if (player.hasTag(PTag.BLUE)) {
            val killer = event.killer
            addTask({ spawn(player) }.runNow(plugin))
            if (killer != null && plugin[killer].hasTag(PTag.RED)) {
                player.apply {
                    removeTag(PTag.BLUE)
                    addTag(PTag.RED)
                }
                comp.send(PLAYER_DEATH_TO_VOID, joined, player)
            } else {
                comp.send(BLUE_TEAM_DEATH, joined, player)
            }
            requestStop()
//            if (gameState !== GameState.STOP) {
//                addTask({ spawn(player) }.runNow(plugin))
//            }
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onLeftGame(event: GameLeftEvent) {
        val player = event.player
        if (isJoined(player)) {
            if (player.hasTag(PTag.RED)) {
                requestStop()
            }
        }
    }

}