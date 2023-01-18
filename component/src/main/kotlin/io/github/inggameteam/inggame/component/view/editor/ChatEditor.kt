package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.model.ActionComponent
import io.github.inggameteam.inggame.component.model.AlertRecivingPlayer
import io.github.inggameteam.inggame.utils.runNow
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

interface ChatEditor : Editor {

    fun get(): String?
    fun set(any: String)

    override fun open(player: Player) {
        var semaphore = false
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
            @EventHandler
            fun onChat(event: AsyncPlayerChatEvent) {
                if (semaphore) return
                semaphore = true
                val message = event.message
                val iTask = block@{
                    if (message == "\$cancel") {
                        player.sendMessage(view[editor, "cancel-edit", String::class])
                        return@block
                    }
                    set(message)
                    previousSelector?.open(player)
                }
                iTask.runNow(plugin)
                event.isCancelled = true
                semaphore = false
                HandlerList.unregisterAll(this)
            }

        }
        plugin.server.pluginManager.registerEvents(listener, plugin)
        get()?.apply {
            ActionComponent(this, ClickEvent.Action.SUGGEST_COMMAND, "", null, null)
                .send(AlertRecivingPlayer(player))
        }
    }

}