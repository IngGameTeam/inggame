package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

interface TeleportOnSpawn : Wrapper {
    val teleportOnSpawn: HashMap<String, LocationModel>
}

class TeleportOnSpawnImp(wrapper: Wrapper) : SimpleWrapper(wrapper), TeleportOnSpawn {
    override val teleportOnSpawn: HashMap<String, LocationModel> by nonNull
}

class TeleportOnSpawnHandler(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = player.joined[::SectionalImp]
        if (isNotHandler(game)) return
        val location = game[::TeleportOnSpawnImp].teleportOnSpawn[game.containerState.name]
            ?.run { game.toRelative(this) }
            ?: return
        player.teleport(location)
    }

}