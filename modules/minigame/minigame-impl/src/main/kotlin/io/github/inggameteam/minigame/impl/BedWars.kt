package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Bed
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerInteractEvent

class BedWars(plugin: GamePlugin) : SimpleGame, TeamCompetitionImpl(plugin), Respawn, InteractingBan, SpawnTeamPlayer {
    override val name get() = "bed-wars"
    override val noInteracts = listOf(
        Material.BLUE_CONCRETE, Material.RED_CONCRETE,
        Material.RED_BED, Material.BLUE_BED,
    )
    private var redBed = true
    private var blueBed = true
    private fun isBedAlive(player: GPlayer) = if (player.hasTag(PTag.RED)) redBed else blueBed

    fun playBedBrokenSound() = joined.forEach { it.playSound(it.eyeLocation, Sound.ENTITY_WITHER_DEATH, 1f, 1f) }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (isJoined(event.player)) {
            val gPlayer = plugin[event.player]
            if (event.block.type === Material.RED_BED) {
                event.isCancelled = true
                if (gPlayer.hasTag(PTag.BLUE)) {
                    redBed = false
                    comp.send("RED-bed", joined)
                    getLocation("RED_BED").block.type = Material.AIR
                    getLocation("RED_BED2").block.type = Material.AIR
                    playBedBrokenSound()
                }
            } else if (event.block.type === Material.BLUE_BED) {
                event.isCancelled = true
                if (gPlayer.hasTag(PTag.RED)) {
                    blueBed = false
                    comp.send("BLUE-bed", joined)
                    getLocation("BLUE_BED").block.type = Material.AIR
                    getLocation("BLUE_BED2").block.type = Material.AIR
                    playBedBrokenSound()
                }
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun interactBed(event: PlayerInteractEvent) {
        if (isJoined(event.player)) {
            val clickedBlock = event.clickedBlock
            if (event.action !== Action.RIGHT_CLICK_BLOCK) return
            if(clickedBlock != null && clickedBlock.state is @Suppress("DEPRECATION") Bed) {
                event.isCancelled = true
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun bedEnter(event: PlayerBedEnterEvent) {
        if (gameState === GameState.PLAY && isJoined(event.player)) event.isCancelled = true
    }

    override fun testRespawn(player: GPlayer) = super.testRespawn(player) && isBedAlive(player)

    override fun sendDeathMessage(player: GPlayer, killer: Player?) {
        comp.send(getPlayerTeam(player).name + "-death" + if (isBedAlive(player)) "" else "-final", joined, player)
    }

    @Suppress("unused")
    @EventHandler
    override fun staticInteractBlock(event: PlayerInteractEvent) {
        listOf(event.material).forEach { mat ->
            staticBreak(event.player, mat, event)
        }
    }

}