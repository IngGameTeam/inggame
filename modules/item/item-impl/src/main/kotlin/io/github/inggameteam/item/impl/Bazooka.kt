package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.VectorUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileHitEvent
import kotlin.random.Random

class Bazooka(override val plugin: AlertPlugin) : Interact, HandleListener(plugin) {
    override val name get() = "bazooka"
    private fun rand(to: Double) = Random.nextDouble(-to, to)

    override fun use(name: String, player: GPlayer) {
        player.apply {
            if (getCooldown(Material.LEATHER_HORSE_ARMOR) > 0) return@apply
            setCooldown(Material.LEATHER_HORSE_ARMOR, 150)
            inventory.getItem(inventory.heldItemSlot)!!.apply { amount -= 1 }
            repeat(10) {
                world.spawn(eyeLocation.add(location.direction.multiply(2)), TNTPrimed::class.java) {
                    it.addScoreboardTag(BAZOOKA)
                    it.velocity =
                        VectorUtil.getDirection(
                            location.yaw + rand(13.0),
                            location.pitch + rand(10.0)
                        ).multiply(2)
                    it.ticksLived = 55
                }
            }
        }

    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: ProjectileHitEvent) {
        val player = event.hitEntity
        if (player !is Player) return
        if (event.entity.scoreboardTags.contains(BAZOOKA)) {
            player.noDamageTicks = 0
        }
    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: EntityDamageByEntityEvent) {
        if (event.damager.scoreboardTags.contains(BAZOOKA)) {
            event.damage = 0.5
        }
    }


    companion object {
        const val BAZOOKA = "bazooka"
    }

}