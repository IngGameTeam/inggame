package io.github.inggameteam.minigame.angangang.game.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasTags
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.boss.BarColor
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class TNTTag(plugin: GamePlugin) : CompetitionImpl(plugin), BarGame, SimpleGame, NoBlockPlace, NoBlockBreak {

    override val bar = GBar(plugin, size = 200.0)
    override val name get() = "tnt-tag"
    var bomber = GPlayerList()

    fun beginBomber() {
        pickBomber()
        bar.tick = 0
        addTask(bar.startTimer {
            bomber.forEach { bomber ->
                bomber.apply { spawnParticle(Particle.EXPLOSION_HUGE, this.eyeLocation, 1) }
                bomber.eyeLocation.apply {
                    world!!.getNearbyEntities(this, 1.0, 1.0, 1.0)
                        .filterIsInstance<LivingEntity>()
                        .forEach { it.damage(10000.0) }
                }
            }
            if (gameState == GameState.PLAY) beginBomber()
        })
    }

    override fun beginGame() {
        super.beginGame()
        beginBomber()
    }

    fun updateBarTitle() {
        if (bomber.isNotEmpty()) bar.update(
            title = "${ChatColor.RED}$bomber",
            progress = 0.0.coerceAtLeast(bar.tick / bar.size),
            color = BarColor.RED
        )
    }

    private fun rightBomberAmount() = joined.hasTags(PTag.PLAY).size/4 + 1

    private fun pickBomber(player: ArrayList<GPlayer> = ArrayList<GPlayer>()
        .apply { repeat(rightBomberAmount()) { add(joined.hasTags(PTag.PLAY).random()) } }) {
        bomber.forEach(::unsetBoom)
        bomber = GPlayerList(player)
        bomber.forEach(::equipBoom)
    }

    private fun unsetBoom(player: GPlayer) {
        player.inventory.clear()
        player.removePotionEffect(PotionEffectType.SPEED)
    }

    private fun equipBoom(player: GPlayer) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 55555, 3))
        player.inventory.contents = comp.inventory("bomber", player.lang(plugin)).contents
    }

    fun changeBomber(origin: GPlayer, new: GPlayer) {
        unsetBoom(origin)
        bomber.remove(origin)
        equipBoom(new)
        bomber.add(new)
        updateBarTitle()
    }

    @Suppress("unused")
    @EventHandler
    fun damageEntity(event: EntityDamageByEntityEvent) {
        if (gameState != GameState.PLAY) return
        val entity = event.entity
        if (entity is Player) {
            if (!isJoined(entity)) return
            event.isCancelled = true
            var attacker: Player? = null
            val damager = event.damager
            if ((damager is Player).apply { if (this) attacker = damager as Player }
                || (damager is Projectile && damager.shooter is Player)
                    .apply { if (this) attacker = damager as Player }) {
                if (bomber.map { it.player }.contains(attacker!!)) {
                    changeBomber(plugin[attacker!!], plugin[entity])
                }
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun interact(event: PlayerInteractEntityEvent) {
        if (gameState != GameState.PLAY) return
        val entity = event.rightClicked
        val gPlayer = plugin[event.player]
        if(bomber.contains(gPlayer) && entity is Player) {
            if (!isJoined(entity)) return
            changeBomber(gPlayer, plugin[entity])
        }
    }

}