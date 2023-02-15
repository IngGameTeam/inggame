package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.item.event.ItemUseEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

class GameChoosingMenuHandler(
    private val gameChoosingMenuHelper: GameChoosingMenuHelper,
    plugin: IngGamePlugin
) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onUseItem(event: ItemUseEvent) {
        val item = event.item[::GameChoosingMenuImp]
        if (isNotHandler(item)) return
//        gameChoosingMenuHelper.open(item[::GameChoosingMenuImp], event.player)
    }

}