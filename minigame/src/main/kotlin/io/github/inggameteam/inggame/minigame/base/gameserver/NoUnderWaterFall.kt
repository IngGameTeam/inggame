package io.github.inggameteam.inggame.minigame.base.gameserver

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.minigame.base.game.GameServer
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockFromToEvent

class NoUnderWaterFall(
    private val gameServer: GameServer,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onBlockMove(event: BlockFromToEvent) {
        val toBlock = event.toBlock
        if (toBlock.type === Material.WATER) {
            if (toBlock.y <= 0) {
                if (isNotHandler(gameServer)) return
                event.isCancelled = true
            }
        }
    }


}