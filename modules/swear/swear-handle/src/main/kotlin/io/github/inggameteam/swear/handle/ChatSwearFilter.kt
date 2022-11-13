package io.github.inggameteam.swear.handle

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.PlayerPlugin
import io.github.inggameteam.scheduler.runNow
import io.github.inggameteam.swear.Swear
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.io.File

class ChatSwearFilter(private val plugin: PlayerPlugin) : HandleListener(plugin) {

    private val swearFilter = Swear(File(plugin.dataFolder, "swears.json"))

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOWEST)
    fun onChat(event: AsyncPlayerChatEvent) {
        val input = event.message
        if (swearFilter.findSwear(input)) {
            event.isCancelled = true
            punish(plugin[event.player], input)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onCmd(event: PlayerCommandPreprocessEvent) {
        val player = plugin[event.player]
        val input = event.message
        if (swearFilter.findSwear(input)) {
            event.isCancelled = true
            punish(player, input)
        }
    }

    private fun punish(player: GPlayer, input: String) {
        ;{
            player.kickPlayer("You were kicked using abusive language\n$input")
        }.runNow(plugin)
    }


}