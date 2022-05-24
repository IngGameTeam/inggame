package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GameAlert.*
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasNoTags
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent

class AvoidAnvil(plugin: GamePlugin) : SimpleGame, CompetitionImpl(plugin), Recorder,
    BeginPlayersAmount, NoBlockBreak, NoBlockPlace {
    override val name get() = "avoid-anvil"
    override val startPlayersAmount get() = 1
    override var beginPlayersAmount = 0
    override fun rewardPoint(player: GPlayer) = if (beginPlayersAmount <= 1) 0 else super.rewardPoint(player)

    override fun calcWinner() {
        if (beginPlayersAmount > 1) {
            val winner = joined.hasTags(PTag.PLAY).hasNoTags(PTag.DEAD)[0]
            joined.forEach { comp.send(SINGLE_WINNER, it, winner, displayName(it)) }
            Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this, GPlayerList(listOf(winner))))
        }
    }

    override fun sendDeathMessage(player: GPlayer) {
        comp.send(PLAYER_DEATH_TO_VOID, joined, player, recordString(player))
    }

    @Suppress("unused")
    @EventHandler
    fun avoidAnvilBegin(event: GameBeginEvent) {
        if (event.game !== this) return
        var count = 0.0
        addTask({
            (0..count.toInt()).forEach { _ -> spawnAnvilRandomly() }
            count += 0.02
            true
        }.repeat(plugin, 1, 1))
    }

    private fun spawnAnvilRandomly() {
        val pos1 = getLocation("pos1")
        val pos2 = getLocation("pos2")
        val world = pos1.world!!
        val x = (pos1.x.toInt()..pos2.x.toInt()).random()
        val y = (pos1.y.toInt()..pos2.y.toInt()).random()
        val z = (pos1.z.toInt()..pos2.z.toInt()).random()
        val location = world.getBlockAt(x, y, z).location
        world.spawnFallingBlock(location.add(.5, .5, .5), Material.ANVIL, 0)
    }

    override fun stop(force: Boolean, leftType: LeftType) {
        super.stop(force, leftType)
        if (gameState != GameState.STOP) {
            point.world.getNearbyEntities(getLocation("start"), 100.0, 100.0, 100.0).forEach {
                if (it.type != EntityType.PLAYER) it.remove()
            }
        }
    }



    @Suppress("unused")
    @EventHandler
    fun onFall(event: EntityChangeBlockEvent) {
        if (!isInSector(event.entity.location)) return
        event.isCancelled = true
        val block = event.block
        joined.hasTags(PTag.PLAY).map(GPlayer::bukkit)
            .filter {
                val location = it.location
                location.block == block || location.add(0.0, -1.0, 0.0).block == block
            }.forEach { it.damage(10000.0) }
    }

    @Suppress("unused")
    @EventHandler
    fun damage(event: EntityDamageByEntityEvent) {
        val player = event.entity
        if (gameState === GameState.PLAY && player is Player && isJoined(player)) {
            event.isCancelled = true
        }
    }

}