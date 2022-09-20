package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.delay
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent
import kotlin.random.Random

class BigBoom(override val plugin: AlertPlugin) : Interact, HandleListener(plugin) {

    override val name get() = "big-boom"
    private fun rand(to: Double) = Random.nextDouble(-to, to)
    override fun use(name: String, player: GPlayer) {
        player.apply {
            if (getCooldown(Material.TNT) > 0) return@apply
            setCooldown(Material.TNT, 20)
            inventory.getItem(inventory.heldItemSlot)!!.apply { amount -= 1 }
            repeat(30) {
                world.spawn(eyeLocation.add(location.direction.multiply(2)), TNTPrimed::class.java) {
                    it.addScoreboardTag(BIG_BOOM_TAG)
                    it.velocity = location.direction.multiply(1.4);
                    {
                        it.location.yaw += rand(5.0).toFloat()
                        it.location.pitch += rand(5.0).toFloat()
                    }.delay(plugin, 4)
                    it.ticksLived = 65
                }
            }
        }

    }

    @Suppress("unused")
    @EventHandler
    fun hit(event: ProjectileHitEvent) {
        val player = event.hitEntity
        if (player !is Player) return
        if (event.entity.scoreboardTags.contains(BIG_BOOM_TAG)) player.noDamageTicks = 0
    }

    companion object {
        const val BIG_BOOM_TAG = "bigBoomTag"
    }

}