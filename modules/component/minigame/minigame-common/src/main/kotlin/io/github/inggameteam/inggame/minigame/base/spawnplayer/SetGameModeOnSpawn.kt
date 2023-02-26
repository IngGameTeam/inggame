package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.GameMode
import org.bukkit.event.EventHandler

interface SetGameModeOnSpawn : Wrapper {
    val gameModeOnSpawn: GameMode
}

class SetGameModeOnSpawnImp(wrapper: Wrapper) : SimpleWrapper(wrapper), SetGameModeOnSpawn {
    override val gameModeOnSpawn: GameMode by nonNull
}

class SetGameModeOnSpawnHandler(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        if (isNotHandler(player)) return
        player.gameMode = player[::SetGameModeOnSpawnImp].gameModeOnSpawn
    }

}