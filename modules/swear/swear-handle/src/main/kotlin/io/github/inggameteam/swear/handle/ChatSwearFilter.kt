package io.github.inggameteam.swear.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.scheduler.runNow
import io.github.inggameteam.swear.Swear
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.Plugin
import java.io.File

class ChatSwearFilter(private val plugin: Plugin) : HandleListener(plugin) {

    private val swearFilter = Swear(File(plugin.dataFolder, "swears.json"))

    @Suppress("unused")
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val input = event.message
        if (swearFilter.findSwear(input).size != 0) {
            ;{
                event.player.kickPlayer("You were kicked using abusive language\n$input")
            }.runNow(plugin)
        }
    }

}