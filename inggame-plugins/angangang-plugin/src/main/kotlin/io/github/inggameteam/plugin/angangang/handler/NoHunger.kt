package io.github.inggameteam.plugin.angangang.handler

import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.player.PlayerPlugin
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Bukkit

class NoHunger(plugin: PlayerPlugin, vararg world: String) : HandleListener(plugin) {

    init {
        {
            val allowed = world.mapNotNull { Bukkit.getWorld(it) }.toList()
            plugin.playerRegister.values.forEach {
                if (allowed.contains(it.world)) it.foodLevel = 20
            }
            true
        }.repeat(plugin, 20, 20)
    }

}