package io.github.inggameteam.minigame.base

import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasTags
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class TeamCompetition(plugin: GamePlugin) : CompetitionImpl(plugin) {

    @Deprecated("EventHandler")
    @EventHandler(priority = EventPriority.LOW)
    fun onBeginInitTeamRandomly(event: GameBeginEvent) {
        randomizeTeam()
    }

    fun getPlayerTeam(player: GPlayer) = if (player.hasTag(PTag.RED)) PTag.RED else PTag.BLUE

    open fun randomizeTeam(redTeamSize: Int = generateHalfSize()) {
        var redTeam = redTeamSize
        joined.hasTags(PTag.PLAY).shuffled().forEach { it.addTag(if (redTeam <= 0) PTag.BLUE else PTag.RED); redTeam-- }
    }

    open fun generateHalfSize(): Int {
        val size = joined.hasTags(PTag.PLAY).size
        return if (size % 2 != 0) listOf(1, 0).random() + size / 2 else size / 2
    }

    override fun stopCheck() {
        if (gameState !== GameState.PLAY) return
        val playPlayers = joined.hasTags(PTag.PLAY)
        if (playPlayers.hasTags(PTag.BLUE).size <= 0) {
            stop(false)
        } else if (playPlayers.hasTags(PTag.RED).size <= 0) {
            stop(false)
        }
    }

    override fun calcWinner() {
        val playPlayers = joined.hasTags(PTag.PLAY).toList()
        val alert = if (playPlayers.firstOrNull()?.hasTag(PTag.RED) == true) GameAlert.RED_TEAM_WIN else GameAlert.BLUE_TEAM_WIN
        comp.send(alert, joined, playPlayers.joinToString(", "))
//        playPlayers.forEach { Context.rewardPoint(it.player, rewardPoint()) }
        Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this, GPlayerList(playPlayers)))
    }



    @Deprecated("EventHandler")
    @EventHandler
    open fun damage(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (player is Player && isJoined(player)) {
            val damager = event.damager
            val attacker = if (damager is Player) damager else if (damager is Projectile) {
                val shooter = damager.shooter
                if (shooter is Player) shooter else return
            } else return
            val gAttacker = plugin[attacker]
            val gPlayer = plugin[player]
            if (
                gAttacker.hasTag(PTag.BLUE) && gPlayer.hasTag(PTag.BLUE)
                || gAttacker.hasTag(PTag.RED) && gPlayer.hasTag(PTag.RED)
            ) event.isCancelled = true
        }
    }

}