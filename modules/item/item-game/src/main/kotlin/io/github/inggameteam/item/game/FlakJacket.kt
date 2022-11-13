package io.github.inggameteam.item.game

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.item.impl.HandyGun
import io.github.inggameteam.player.GPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent

class FlakJacket(override val plugin: AlertPlugin) : Item, HandleListener(plugin) {
    override val name get() = "flak-jacket"
    override fun use(name: String, player: GPlayer) = Unit

    @Suppress("unused")
    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        if (event.entityType === EntityType.PLAYER
            && damager is Projectile && damager.scoreboardTags.contains(HandyGun.GUN_TAG)) {
            val player = plugin[event.entity as Player]
            val inventory = player.inventory
            ItemSlot.CHEST_PLATE.slot.any {nameOrNull(player, inventory.getItem(it)) !== null}
        }
    }

}