package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InteractCancel
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.player.GPlayer
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Fireball
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.ProjectileHitEvent

class Meteor(override val plugin: AlertPlugin, val purchase: PurchaseContainer) : Item, InteractCancel, HandleListener(plugin) {
    override val name get() = "meteor"

    override fun use(name: String, player: GPlayer) {
        val playerPurchase = purchase[player][name]
        playerPurchase.amount -= 1
        player.inventory.itemInMainHand.apply { amount = playerPurchase.amount }
        player.apply {
            world.spawn(eyeLocation.add(location.direction.multiply(2)), Fireball::class.java) {
                it.shooter = player.bukkit
                it.addScoreboardTag(METEOR_TAG)
            }
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onEntityBlockChange(event: EntityExplodeEvent) {
        val entity = event.entity
        if (entity is Projectile && entity.scoreboardTags.contains(METEOR_TAG)) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun projectileHit(event: ProjectileHitEvent) {
        val hitBlock = event.hitBlock
        val entity = event.entity
        val hitEntity = event.hitEntity
        if (entity.scoreboardTags.contains(METEOR_TAG)) {
            if (hitBlock != null) {
                val location = entity.location
                location.world!!.apply {
                    spawnParticle(Particle.EXPLOSION_LARGE, location, 1)
                    playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.65f, 1f)
                }
            } else if (hitEntity != null) {
                if (hitEntity is LivingEntity) {
                    return
                }
            }
            entity.remove()
            event.isCancelled = true
        }
    }

    companion object {
        const val METEOR_TAG = "Meteor"
    }
}