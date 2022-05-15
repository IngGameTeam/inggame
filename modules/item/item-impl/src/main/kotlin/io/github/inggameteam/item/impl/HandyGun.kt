package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.player.GPlayer
import org.bukkit.Particle
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerPickupArrowEvent

class HandyGun(override val plugin: AlertPlugin) : Interact {
    override val name get() = "handy-gun"

    override fun use(name: String, player: GPlayer) {
        player.apply {
            world.spawn(eyeLocation.add(location.direction.multiply(1.1)), Arrow::class.java) {
                it.addScoreboardTag(GUN_TAG)
                it.velocity = location.direction.multiply(itemComponent.doubleOrNull("$name-power")?: 5.0)
                it.shooter = player.bukkit
            }
        }

    }

    @Deprecated("EventHandler")
    @EventHandler
    fun projectileHit(event: ProjectileHitEvent) {
        val hitBlock = event.hitBlock
        val entity = event.entity
        val hitEntity = event.hitEntity
        if (entity.scoreboardTags.contains(GUN_TAG)) {

            if (hitBlock != null) {
                val location = entity.location
                location.world!!.spawnParticle(Particle.ASH, location, 21)
            } else if (hitEntity != null) {
                if (hitEntity is LivingEntity) {
                    return
                }
            }
            entity.remove()
            event.isCancelled = true

        }
    }

    @Deprecated("EventHandler")
    @EventHandler
    fun pickUpArrow(event: PlayerPickupArrowEvent) {
        if (event.arrow.scoreboardTags.contains(GUN_TAG)) {
            event.arrow.remove()
            event.isCancelled = true
        }
    }

    companion object {
        const val GUN_TAG ="GunItemTag"
    }

}
