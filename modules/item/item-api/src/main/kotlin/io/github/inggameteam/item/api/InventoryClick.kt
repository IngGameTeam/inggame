package io.github.inggameteam.item.api

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent

interface InventoryClick : Item {

    @Suppress("unused")
    @EventHandler
    fun onClickInventory(event: InventoryClickEvent) {
        var whoClicked = event.whoClicked
        if (whoClicked !is Player) return
        val player = plugin[whoClicked]
        val name = nameOrNull(player, event.currentItem)?: return
        use(name, player)
    }

}