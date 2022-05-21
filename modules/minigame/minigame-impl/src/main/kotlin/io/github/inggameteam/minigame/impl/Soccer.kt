package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.entity.Animals
import org.bukkit.entity.Cow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Soccer(plugin: GamePlugin) : TeamCompetitionImpl(plugin),
    SpawnTeamPlayer, NoDamage, NoBlockPlace, NoBlockBreak, SimpleGame {
    override val name get() = "soccer"
    var entities = ArrayList<Animals>()
    var underY = -1.0
    var centerZ = -1.0
    var blueScore = 0
    var redScore = 0

    private fun summonEntity() {
        val loc = getLocation("start")
        underY = loc.blockY - 0.5
        centerZ = loc.z
        entities.add(loc.world!!.spawn(loc, Cow::class.java) {
            it.maxHealth = 10000.0
            it.health = it.maxHealth
        })
    }

    @Suppress("unused")
    @EventHandler
    fun animalDamage(event: EntityDamageByEntityEvent) {
        val player = event.damager
        if (entities.contains(event.entity) && player is Player) {
            event.entity.velocity = player.location.direction.multiply(.5)
            event.isCancelled = true
        }
    }

    private fun addScore(team: PTag, amount: Int = 1) {
        if (team === PTag.RED) redScore += amount else blueScore += amount
        if (redScore >= GOAL_SCORE || blueScore >= GOAL_SCORE) {
            joined.hasTags(team).forEach { it.addTag(PTag.DEAD); it.removeTag(PTag.PLAY) }
        }
        stopCheck()
        if (gameState !== GameState.STOP) {
            summonEntity()
            comp.send("score", joined, blueScore, redScore)
        }
    }

    override fun beginGame() {
        super.beginGame()
        summonEntity()
        gameTask = ITask.repeat(plugin, 1, 1, {
            entities.removeIf { it.isDead }
            if (entities.isEmpty()) summonEntity()
            entities.forEach { it.target = null }
            entities.filter { it.location.y <= underY }.forEach {
                if (centerZ < it.location.z) {
                    addScore(PTag.RED)
                } else {
                    addScore(PTag.BLUE)
                }
                it.remove()
            }
        })
    }

    companion object {
        const val GOAL_SCORE = 3
    }

}