package io.github.inggameteam.minigame

import io.github.inggameteam.utils.IntVector
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class TestPlugin : GamePluginImpl(
    hubName = "hub",
    worldName = "customized_world",
    worldSize = IntVector(300, 128),
) {
    override fun onEnable() {
        super.onEnable()
        component.string["enable_plugin"]?.apply { logger.info(this) }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        gameRegister.join(event.player, "hub")
    }
}