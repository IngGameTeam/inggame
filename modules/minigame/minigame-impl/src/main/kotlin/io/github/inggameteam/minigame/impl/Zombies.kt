package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GameAlert.PLAYER_DEATH_TO_VOID
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
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
    var roundDone = false
    override fun rewardPoint(player: GPlayer): Int {
        if (roundDone) {
            return 1500
        }
        return if (beginPlayersAmount <= 1) 0 else super.rewardPoint(player)
    }

    override fun finishGame() {
        if (roundDone) {
            comp.send("round-done", joined, joined.hasTags(PTag.PLAY))
        }
        super.finishGame()
    }

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
            var returnValue = true
            val center = getLocation("start")
            val lives = point.world.getNearbyEntities(center, 50.0, 50.0, 50.0) { it.scoreboardTags.contains(ZOMBIE_TAG) }
            if (lives.isEmpty()) {
                if (round > MAX_ROUND) {
                    roundDone = true
                    stop(false)
                    returnValue = false
                }
                runZombiesRounds()
                comp.send("new-rounds", joined, round)
                round++
            }
            returnValue
        }.repeat(plugin, 0, 1))
    }

    private fun spawnZombies() {
        val location = getLocation(comp.stringList("zombie-spawn-locations", plugin.defaultLanguage).random())
        point.world.spawn(location, Zombie::class.java).apply {
            addScoreboardTag(ZOMBIE_TAG)
            isBaby = false
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
        if (event.damager !is Player) return
        if (gameState === GameState.PLAY && player is Player && isJoined(player)) {
            event.isCancelled = true
        }
    }

    companion object {
        const val ZOMBIE_TAG = "ZombiesZombieTag"
        const val MAX_ROUND = 15
    }

}