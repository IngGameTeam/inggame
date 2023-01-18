package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.model.ActionComponent
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.utils.runNow
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class StringEditor(
    nameSpaceView: NameSpaceView,
    override val previousSelector: Selector<*>?,
    val set: (String) -> Unit, val get: () -> String
) : Editor, NameSpaceView by nameSpaceView {

    override fun open(player: Player) {
        var semaphore = false
        object : Listener {
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
                ;
                val iTask = block@{
                    if (message == "\$cancel") {
                        player.sendMessage(view[editor, "cancel-edit", String::class])
                        return@block
                    }
                    set(message)
                }
                iTask.runNow(plugin)
                event.isCancelled = true
                semaphore = false
                HandlerList.unregisterAll(this)
            }

        }
        ActionComponent(get(), ClickEvent.Action.SUGGEST_COMMAND, "", null, null)
    }

}