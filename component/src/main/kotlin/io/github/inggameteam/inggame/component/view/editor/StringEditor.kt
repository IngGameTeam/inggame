package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.model.ActionComponent
import io.github.inggameteam.inggame.component.model.AlertRecivingPlayer
import io.github.inggameteam.inggame.component.view.model.editor.EditorView
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
    view: EditorView<String>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<String> by view, ChatEditor {

    override fun set(any: String) { set(any) }
    override fun get(): String? = get()

}