package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GameAlert.PLAYER_DEATH_TO_VOID
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.repeat
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Zombies(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), Recorder,
    BeginPlayersAmount, NoBlockBreak, NoBlockPlace {
    override val name get() = "zombies"
    override var beginPlayersAmount = 0
    var round: Int = 1
    override fun rewardPoint(player: GPlayer) = if (beginPlayersAmount <= 1) 0 else super.rewardPoint(player)


    override fun calcWinner() = Unit

    override fun sendDeathMessage(player: GPlayer, killer: Player?) {
        comp.send(PLAYER_DEATH_TO_VOID, joined, player, round, recordString(player))
    }

    @Suppress("unused")
    @EventHandler
    fun beginZombies(event: GameBeginEvent) {
        if (event.game !== this) return
        runRoundCheck()
    }

    fun runRoundCheck() {
        addTask({
            val center = getLocation("start")
            val lives = point.world.getNearbyEntities(center, 50.0, 50.0, 50.0) { it.scoreboardTags.contains(ZOMBIE_TAG) }
            if (lives.isEmpty()) {
                runZombiesRounds()
                round++
                comp.send("new-rounds", joined, round)
            }
            true
        }.repeat(plugin, 0, 1))
    }

    private fun spawnZombies() {
        val location = getLocation(comp.stringList("zombie-spawn-locations", plugin.defaultLanguage).random())
        point.world.spawn(location, Zombie::class.java).apply {
            addScoreboardTag(ZOMBIE_TAG)
        }
    }



    fun runZombiesRounds() {
        var i = 0
        addTask({
            if (i++ < round) {
                spawnZombies()
                true
            } else false
        }.repeat(plugin, 0, 1))
    }

    @Suppress("unused")
    @EventHandler
    fun damage(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (gameState === GameState.PLAY && player is Player && isJoined(player)) {
            event.isCancelled = true
        }
    }

    companion object {
        const val ZOMBIE_TAG = "ZombiesZombieTag"
    }

}