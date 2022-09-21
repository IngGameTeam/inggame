package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
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
import org.bukkit.boss.BarColor
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class BlockHideAndSeek(plugin: GamePlugin) : InfectionImpl(plugin),
    SpawnTeamPlayer, VoidDeath, SimpleGame, NoBlockPlace, NoBlockBreak, BarGame {

    override val name get() = "block-hide-and-seek"
    override val bar by lazy { GBar(plugin, size = 750.0, reversed = true) }
    var isWaiting = true

    override fun beginGame() {
        super.beginGame()
        isWaiting = true
        joined.hasTags(PTag.PLAY, PTag.RED).forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 5555555, 1))
            it.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 5555555, 1))
        }
        bar.size = 300.0
        bar.update(alert = { comp.string("waiting-title", it.lang(plugin) )}, color = BarColor.GREEN)
        gameTask = bar.startTimer {
            isWaiting = false
            joined.forEach { comp.send("waiting-end", it, displayName(it)) }
            joined.hasTags(PTag.PLAY, PTag.RED).forEach {
                it.removePotionEffect(PotionEffectType.BLINDNESS)
                it.removePotionEffect(PotionEffectType.SLOW)
            }
        }
        bar.size = 750.0
        bar.update(alert = { comp.string("left-time-title", it.lang(plugin) )}, color = BarColor.PINK)
        gameTask = bar.startTimer {
            joined.hasTags(PTag.PLAY, PTag.RED).forEach {
                it.removeTag(PTag.PLAY)
            }
            requestStop()
        }

    }

    private val GPlayer.entityKey get() = "${uniqueId.fastToString()}-entity"

    @Suppress("unused")
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

    @Suppress("unused")
    @EventHandler
    fun leftGame(event: GameLeftEvent) {
        val player = event.player
        if (!isJoined(player)) return
        (playerData[player]!![player.entityKey] as? FallingBlock)?.remove()
    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: BlockIgniteEvent) {
        val player = event.player
        if (player !is Player) return
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        hit(gPlayer, event.block.location)
    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: EntityDamageByEntityEvent) {
        val player = event.damager
        if (player !is Player) return
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        hit(gPlayer, event.entity.location.block.location)
    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: PlayerInteractEvent) {
        val player = event.player
        if (isJoined(player).not()) return
        val gPlayer = plugin[player]
        val clickedBlock = event.clickedBlock?: return
        hit(gPlayer, clickedBlock.location)
    }

    private fun hit(gPlayer: GPlayer, location: Location) {
        if (isWaiting) return
        joined.hasTags(PTag.BLUE).forEach {
            if (it.location.block.location == location) {
                if (gPlayer.hasTag(PTag.RED) && it.hasTag(PTag.BLUE)) {
                    (playerData[it]!![gPlayer.uniqueId.fastToString()] as? Location)
                        ?.apply { joined.hasTags(PTag.PLAY).forEach { p ->
                            p.sendBlockChange(this, Material.AIR.createBlockData())
                        } }
                    (playerData[it]!![it.entityKey] as? FallingBlock)?.remove()
                    Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(it, gPlayer.bukkit))
                }
            }
        }
    }


}