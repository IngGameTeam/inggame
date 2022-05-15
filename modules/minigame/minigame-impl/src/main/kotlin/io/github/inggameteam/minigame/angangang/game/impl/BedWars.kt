package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Bed
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerInteractEvent

class BedWars(plugin: GamePlugin) : SimpleGame, TeamCompetition(plugin), Respawn, InteractingBan, SpawnTeamPlayer {
    override val name get() = "bed-wars"
    override val noInteracts = listOf(Material.BLUE_CONCRETE, Material.RED_CONCRETE)
    private var redBed = true
    private var blueBed = true
    private fun isBedAlive(player: GPlayer) = if (player.hasTag(PTag.RED)) redBed else blueBed

    fun playBedBrokenSound() = joined.forEach { it.playSound(it.eyeLocation, Sound.ENTITY_WITHER_DEATH, 1f, 1f) }

    @Suppress("unused")
    @EventHandler
    fun interactBed(event: PlayerInteractEvent) {
        if (isJoined(event.player)) {
            val clickedBlock = event.clickedBlock
            if (event.action !== Action.RIGHT_CLICK_BLOCK) return
            if(clickedBlock != null && clickedBlock.state is Bed) {
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

    override fun sendDeathMessage(player: GPlayer) {
        comp.send(getPlayerTeam(player).name + "-death", joined, player)
    }

}