package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.Sector
import io.github.inggameteam.minigame.angangang.game.base.SimpleGame
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.repeat
import io.github.inggameteam.utils.fastToString
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class HideAndSeek(plugin: GamePlugin, point: Sector) : InfectionImpl(plugin, point), SpawnTeamPlayer, VoidDeath, SimpleGame {

    override val name get() = "hide-and-seek"

    val GPlayer.entityKey get() = "${uniqueId.fastToString()}-entity"

    @EventHandler
    fun onBeginHideAndSeek(event: GameBeginEvent) {
        if (this !== event.game) return
        joined.hasTags(PTag.BLUE).forEach {
            val createBlockData = mats(it).keys.random().createBlockData()
            playerData[it]!![BLOCK] = createBlockData
            comp.alert("seek", it.lang(plugin)).send(it, mats(it)[createBlockData.material]!!)
        }
        addTask({
            joined.hasTags(PTag.BLUE).forEach { p ->
                p.apply {
                    val blockData = playerData[p]!![BLOCK] as BlockData
                    val entityKey = entityKey
                    val blockLocation = location.add(.0, 0.5, .0).block.location
                    fun summon() {
                        val any = playerData[p]!![entityKey] as? FallingBlock
                        val fallingBlockLoc = blockLocation.clone().add(0.5, .0, 0.5)
                        any?.remove()
                        blockLocation.world!!.spawnFallingBlock(fallingBlockLoc, blockData)
                            .apply {
                                this.setGravity(false)
                                playerData[p]!![entityKey] = this
                            }
                    }
                    joined.filterNot { it == p }.forEach {
                        (playerData[it]!![uniqueId.fastToString()] as? Location)
                            ?.apply { it.sendBlockChange(this, Material.AIR.createBlockData()) }
//                        (playerData[p]!![entityKey] as? FallingBlock)?.apply { remove() }
                        playerData[it]!![uniqueId.fastToString()] = blockLocation
                        it.sendBlockChange(blockLocation, blockData)
                    }
                    summon()

                }
            }
            true
        }.repeat(plugin, 1, 1))

    }

    fun mats(player: GPlayer, lang: String = player.lang(plugin)) =
        comp.stringList("mats", lang).map { Pair(Material.valueOf(it), comp.string(it, lang)) }.toMap()

    companion object {
        const val BLOCK = "seekBlock"
    }

    @EventHandler
    fun leftGame(event: GameLeftEvent) {
        val player = event.player
        if (!isJoined(player)) return
        (playerData[player]!![player.entityKey] as? FallingBlock)?.remove()
    }

    @EventHandler
    fun hit(event: BlockIgniteEvent) {
        val player = event.player
        if (player !is Player) return
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        hit(gPlayer, event.block.location)
    }

    @EventHandler
    fun hit(event: EntityDamageByEntityEvent) {
        val player = event.damager
        if (player !is Player) return
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        hit(gPlayer, event.entity.location.block.location)
    }

    @EventHandler
    fun hit(event: PlayerInteractEvent) {
        val player = event.player
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        val clickedBlock = event.clickedBlock?: return
        hit(gPlayer, clickedBlock.location)
    }

    fun hit(gPlayer: GPlayer, location: Location) {
        joined.hasTags(PTag.BLUE).forEach {
            if (it.location.block.location == location) {
                if (gPlayer.hasTag(PTag.RED) && it.hasTag(PTag.BLUE)) {
                    (playerData[it]!![gPlayer.uniqueId.fastToString()] as? Location)
                        ?.apply { it.sendBlockChange(this, Material.AIR.createBlockData()) }
                    Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(it, gPlayer.player))
                }
            }
        }
    }


}