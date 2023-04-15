package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.model.LocationModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp
import io.github.inggameteam.inggame.player.ContainerState
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

interface TeleportOnSpawn : Wrapper {
    val teleportOnSpawn: LocationModel?
}

class TeleportOnSpawnImp(wrapper: Wrapper) : SimpleWrapper(wrapper), TeleportOnSpawn {
    override val teleportOnSpawn: LocationModel? by nullable
}

@Deprecated("not referenced")
class TeleportOnSpawnHandler(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onGamePlayerSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        val game = player.joined[::SectionalImp]
        Bukkit.broadcastMessage("TeleportOnSpawn.1")
        if (isNotHandler(game)) return
        Bukkit.broadcastMessage("TeleportOnSpawn.2")
        if (game.containerState !== ContainerState.PLAY) return
        Bukkit.broadcastMessage("TeleportOnSpawn.3")
        val location = game[::TeleportOnSpawnImp].teleportOnSpawn
            ?.run { game.toRelative(this) }
            ?: return
        Bukkit.broadcastMessage("TeleportOnSpawn.4")
        player.teleport(location)
    }

}