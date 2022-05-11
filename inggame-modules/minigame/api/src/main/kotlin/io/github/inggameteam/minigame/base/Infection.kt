package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.base.Infection.Companion.ORIGINAL_INFECTED
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.hasNoTags
import io.github.inggameteam.player.hasTags
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent

interface Infection {
    @EventHandler
    fun onBeginInfection(event: GameBeginEvent) {
        if (event.game !== this) return
        joined.hasTags(PTag.RED).forEach { playerData[it]!![ORIGINAL_INFECTED] = true }
    }
    companion object {
        const val ORIGINAL_INFECTED = "originalInfected"
    }
}

abstract class InfectionImpl(plugin: GamePlugin) : TeamCompetition(plugin) {


//    open fun updateBar() = bar.update("생존자 비상 탈출", color = BarColor.PURPLE)


    override fun calcWinner() {
        joined.hasTags(PTag.PLAY).hasNoTags(PTag.DEAD)
            .filter { playerData[it]!![ORIGINAL_INFECTED] == true }.apply {
                if (isEmpty()) super.calcWinner()
                else comp.send(GameAlert.RED_TEAM_WIN, joined, joinToString(", "))
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
    override fun death(event: GPlayerDeathEvent) {
        if (!isJoined(event.player) || gameState === GameState.WAIT) return
        val player = event.player
        if (player.hasTag(PTag.RED)) {
//            addTask({ spawn(player) }.runNow(plugin))
            comp.send(GameAlert.RED_TEAM_DEATH, joined, player)
        } else if (player.hasTag(PTag.BLUE)) {
            val killer = event.killer
            if (killer != null && plugin[killer].hasTag(PTag.RED)) {
                player.apply {
                    removeTag(PTag.BLUE)
                    addTag(PTag.RED)
                }
                comp.send(GameAlert.PLAYER_DEATH_TO_VOID, joined, player)
            } else {
                comp.send(GameAlert.BLUE_TEAM_DEATH, joined, player)
            }
            stopCheck()
//            if (gameState !== GameState.STOP) {
//                addTask({ spawn(player) }.runNow(plugin))
//            }
        }
    }

    override fun damage(event: EntityDamageByEntityEvent) = Unit

    override fun generateHalfSize() = (joined.hasTags(PTag.PLAY).size/ 5.0).toInt().plus(1)

}