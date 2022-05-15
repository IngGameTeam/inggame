package io.github.inggameteam.item.impl

import io.github.inggameteam.player.GPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

interface Interact : Item {

    @Deprecated("EventHandler")
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, event.item)?: return
        use(name, player)
    }

    @Deprecated("EventHandler")
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = plugin[event.player]
        val name = nameOrNull(player, player.inventory.getItem(event.hand))?: return
        use(name, player)
    }

    fun use(name: String, player: GPlayer)

}