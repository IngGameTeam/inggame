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
        println("itemUSe")
        if (isNotHandler(event.item)) return
        gameChoosingMenuHelper.open(event.item[::GameChoosingMenuImp], event.player)
    }

}