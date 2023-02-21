package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

class TeleportOnSpawn(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = player.joinedGame[::SectionalImp]
        if (isNotHandler(game)) return
        player.teleport(game.getLocationOrNull(game.gameState.name)?: return)
    }

}