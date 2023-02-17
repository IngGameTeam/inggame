package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.item.event.ItemUseEvent
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GamePlayerService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler

class GameChoosingMenuHandler(
    private val gameChoosingMenuHelper: GameChoosingMenuHelper,
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onUseItem(event: ItemUseEvent) {
        val item = event.item[::GameChoosingMenuImp]
        if (isNotHandler(item)) return
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        gameChoosingMenuHelper.open(
            item[::GameChoosingMenuImp],
            player
        )
    }

}