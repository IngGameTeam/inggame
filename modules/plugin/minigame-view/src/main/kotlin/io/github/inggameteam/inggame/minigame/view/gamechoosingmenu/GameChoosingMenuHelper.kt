package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.minigame.base.game.GameHelper
import io.github.inggameteam.inggame.minigame.component.GameResourceService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.entity.Player

class GameChoosingMenuHelper(
    val gameHelper: GameHelper,
    val gameResourceService: GameResourceService,
    val plugin: IngGamePlugin
) {

    fun open(menu: GameChoosingMenu, player: Player) {
        Gui.frame(plugin, menu.lines, menu.title)
            .list(0, 0, 9, menu.lines, {menu.icons.entries.toList()}, {it.value.itemStack}) { list, gui ->
                list.onClick { _, _, pair, event ->
                    val game = pair.second.key
                }

            }
            .openInventory(player)
    }

}