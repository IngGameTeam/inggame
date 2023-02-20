package io.github.inggameteam.inggame.component.view.controller

import org.bukkit.entity.Player
import org.bukkit.event.*
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

class ItemStackEditor(editorView: EditorView<Any>, override val previousSelector: Selector<*>?)
    : Editor, EditorView<Any> by editorView {

    override fun open(player: Player) {
        val listener = object : Listener {
            @Suppress("unused")
            @EventHandler
            fun onQuit(event: PlayerQuitEvent) {
                if (event.player == player) HandlerList.unregisterAll(this)
            }

            @Suppress("unused")
            @EventHandler
            fun onKick(event: PlayerKickEvent) {
                if (event.player == player) HandlerList.unregisterAll(this)
            }

            @Suppress("unused")
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            fun onClick(event: PlayerInteractEvent) {
                onClick(event, event.player, event.item)
            }

            @Suppress("unused")
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            fun onClick(event: EntityDamageByEntityEvent) {
                val p = event.entity
                if (p !is Player) return
                onClick(event, p, p.itemInUse)
            }

            @Suppress("unused")
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            fun onClick(event: InventoryClickEvent) {
                val p = event.whoClicked as Player
                if (event.clickedInventory != p.openInventory.topInventory)
                onClick(event, p, event.currentItem)
            }


            fun onClick(event: Cancellable, eventPlayer: Player, item: ItemStack?) {
                if (player != eventPlayer) return
                if (item === null) {
                    player.sendMessage(editor.VIEW_CANCEL_EDIT)
                    event.isCancelled = true
                    HandlerList.unregisterAll(this)
                    return
                }
                try {
                    set(item)
                } catch (_: Throwable) {
                    player.sendMessage(editor.VIEW_CANCEL_EDIT)
                }
                previousSelector?.open(player)
                event.isCancelled = true
                HandlerList.unregisterAll(this)
            }

        }
        player.closeInventory()
        plugin.server.pluginManager.registerEvents(listener, plugin)
        player.sendMessage(editor.VIEW_CLICK_ITEM_TO_EDIT)
    }

}