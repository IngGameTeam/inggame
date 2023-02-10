package io.github.inggameteam.inggame.component.view.controller

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

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
                if (event.player != player) return
                val item = event.item
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