package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import io.github.inggameteam.inggame.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class SpawnPlayer(plugin: IngGamePlugin) : Handler, Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = player.joinedGame[::SectionalImp]
        if (isNotHandler(game)) return
        player.teleport(game.getLocation("spawn"))
    }

}