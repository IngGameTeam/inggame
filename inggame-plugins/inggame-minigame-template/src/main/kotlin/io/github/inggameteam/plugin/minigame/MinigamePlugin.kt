package io.github.inggameteam.plugin.minigame

import io.github.inggameteam.minigame.GamePluginImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class MinigamePlugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_world",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
    ),
    ) {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        gameRegister.join(event.player, hubName)
    }

}