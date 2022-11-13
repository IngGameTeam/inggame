package io.github.inggameteam.minigame.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.mongodb.impl.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.Plugin

class ChatLogger(plugin: Plugin, private val chat: Chat) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onChat(event: AsyncPlayerChatEvent) {
        chat.chat(event.player.uniqueId, event.message, System.currentTimeMillis())
    }
}