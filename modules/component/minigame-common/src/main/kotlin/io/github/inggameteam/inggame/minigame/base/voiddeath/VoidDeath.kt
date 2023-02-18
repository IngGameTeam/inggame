package io.github.inggameteam.inggame.minigame.base.voiddeath

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.die
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent

interface VoidDeath : Wrapper {
    val voidDeath: Int
}

class VoidDeathImp(wrapper: Wrapper) : VoidDeath, SimpleWrapper(wrapper) {

    override val voidDeath: Int by nonNull

}

class VoidDeathHandler(
    private val gamePlayerService: GamePlayerService,
    private val voidDeathHelper: VoidDeathHelper,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onVoidDeath(event: PlayerMoveEvent) {
        val bPlayer = event.player
        val player = gamePlayerService[bPlayer.uniqueId, ::GPlayer]
        if (voidDeathHelper.testVoidDeath(player)) {
            bPlayer.die()
        }
    }

}