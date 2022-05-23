package io.github.inggameteam.item.game

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Item
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class FireWorks(override val plugin: AlertPlugin, val purchase: PurchaseContainer) : Item, HandleListener(plugin) {

    override val name get() = "fire-works"

    override fun use(name: String, player: GPlayer) {
        purchase[player][this.name].amount -= 1
    }

    @Suppress("unused")
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, event.item)?: return
        when(event.action) {
            Action.RIGHT_CLICK_BLOCK -> use(name, player)
            Action.RIGHT_CLICK_AIR -> if (player.isFlying) use(name, player)
            else -> {}
        }
    }
}