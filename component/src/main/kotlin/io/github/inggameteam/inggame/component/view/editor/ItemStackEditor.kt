package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.selector.Selector
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
                    player.sendMessage(view[editor, "cancel-edit", String::class])
                    event.isCancelled = true
                    HandlerList.unregisterAll(this)
                    return
                }
                try {
                    set(item)
                } catch (_: Throwable) {
                    player.sendMessage(view[editor, "cannot-edit", String::class])
                }
                previousSelector?.open(player)
                event.isCancelled = true
                HandlerList.unregisterAll(this)
            }

        }
        player.closeInventory()
        plugin.server.pluginManager.registerEvents(listener, plugin)
        player.sendMessage(view[editor, "click-item-to-edit", String::class])
    }

}