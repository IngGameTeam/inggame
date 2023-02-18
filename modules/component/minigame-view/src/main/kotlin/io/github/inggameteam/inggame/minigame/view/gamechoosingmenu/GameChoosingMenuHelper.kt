package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.minigame.base.game.GameHelper
import io.github.inggameteam.inggame.minigame.base.game.JoinType
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.component.GameInstanceService
import io.github.inggameteam.inggame.minigame.component.GameResourceService
import io.github.inggameteam.inggame.utils.IngGamePlugin

class GameChoosingMenuHelper(
    private val gameHelper: GameHelper,
    private val gameInstanceService: GameInstanceService,
    val gameResourceService: GameResourceService,
    val plugin: IngGamePlugin
) {

    fun open(menu: GameChoosingMenu, player: GPlayer) {
        Gui.frame(plugin, menu.lines, menu.title)
            .list(0, 0, 9, menu.lines,
                {menu.icons.entries.toList()},
                {it.value.itemStack}) { list, gui ->
                list.onClick { _, _, pair, event ->
                    val game = pair.second.key
                    gameHelper.requestJoin(JoinType.PLAY, game, listOf(player))
                }
            }
            .openInventory(player)
    }

}