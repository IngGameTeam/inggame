package io.github.inggameteam.item.api

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerDropItemEvent

interface Drop : Item {

    @Deprecated("EventHandler")
    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, event.itemDrop.itemStack)?: return
        use(name, player)
    }
}