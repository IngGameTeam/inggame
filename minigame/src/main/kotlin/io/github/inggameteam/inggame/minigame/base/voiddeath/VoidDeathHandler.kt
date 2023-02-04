package io.github.inggameteam.inggame.minigame.base.voiddeath

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.player.PlayerInstanceService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.die
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

class VoidDeathHandler(
    private val gameInstanceService: PlayerInstanceService,
    plugin: IngGamePlugin
) : Handler, HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onVoidDeath(event: PlayerMoveEvent) {
        val bPlayer = event.player
        val player = gameInstanceService[bPlayer.uniqueId, ::VoidDeathImp]
        if (isNotHandler(player)) return
        if (bPlayer.location.y <= player.voidDeath) {
            bPlayer.die()
        }
    }

}