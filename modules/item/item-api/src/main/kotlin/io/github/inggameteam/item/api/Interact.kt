package io.github.inggameteam.item.api

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

interface Interact : Item {

    @Suppress("unused")
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, event.item)?: return
        use(name, player)
    }

    @Suppress("unused")
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, player.inventory.getItem(event.hand))?: return
        use(name, player)
    }

    @Suppress("unused")
    @EventHandler
    fun onInventoryInteract(event: InventoryClickEvent) {
        if (event.click !== ClickType.NUMBER_KEY) return
        val whoClicked = event.whoClicked
        if (whoClicked !is Player) return
        val player = plugin[whoClicked]
        val name = nameOrNull(player, event.currentItem)?: return
        use(name, player)
    }


}