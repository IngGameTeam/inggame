package io.github.inggameteam.plugin.minigame

import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.utils.IntVector
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class MinigamePlugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_world",
    worldSize = IntVector(300, 128),
    init = arrayOf(
        ::Hub,
    ),
    ) {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        gameRegister.join(event.player, hubName)
    }

}