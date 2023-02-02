package io.github.inggameteam.inggame.minigame.handler

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.component.PropHandler
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.wrapper.game.SpawnPlayerImp
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

@PropHandler
class SpawnPlayer(plugin: IngGamePlugin) : Handler, HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = (player.joinedGame?: return)[::SpawnPlayerImp]
        if (isNotHandler(game)) return
        player.teleport(game.spawn.toLocation())
    }

}