package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.VectorUtil
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.random.Random

class ShotGun(override val plugin: AlertPlugin) : Interact {
    override val name get() = "shot-gun"

    private fun rand(to: Double) = Random.nextDouble(-to, to)

    override fun use(name: String, player: GPlayer) {
        player.apply {
            if (getCooldown(Material.IRON_HORSE_ARMOR) > 0) return@apply
            setCooldown(Material.IRON_HORSE_ARMOR, 15)
            repeat(10) {
                world.spawn(eyeLocation.add(location.direction.multiply(2)), Arrow::class.java) {
                    it.addScoreboardTag(ANG_WAR_SHOT_GUN)
                    it.shooter = player.player
                    it.velocity = location.direction.multiply(1.5)
                    val delay = delay@{
                        if (it.isOnGround) return@delay
                        val vel = it.velocity
                        VectorUtil.fromDirection(vel).apply {
                            it.velocity = VectorUtil.getDirection(
                                first + rand(7.5),
                                second + rand(7.5)
                            ).multiply(vel.let { 1.5 })
                        }

                    }
                    delay.delay(plugin, 1)
                    it.ticksLived = 25
                }
            }
        }

    }

    @Deprecated("EventHandler")
    @EventHandler
    fun hit(event: EntityDamageByEntityEvent) {
        if (event.damager.scoreboardTags.contains(ANG_WAR_SHOT_GUN)) {
            event.damage = 0.5
            val entity = event.entity
            if (entity is Player) entity.noDamageTicks = 0
        }
    }

    companion object {
        const val ANG_WAR_PROPS = "angWarProps"
        const val ANG_WAR_SHOT_GUN = "angWarShotGun"
    }

}