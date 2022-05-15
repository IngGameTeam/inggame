package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.player.GPlayer
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.frame.InvFrame
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component

class MinigameMenu(override val plugin: GamePlugin) : Interact, Drop, InventoryClick {
    override val name get() = "game-menu"
    override fun use(name: String, player: GPlayer) {
        minigameMenu(player)
    }

    private val games get() = plugin.gameSupplierRegister.keys.toList().filter { it != plugin.gameRegister.hubName }.toList()

    private fun minigameMenu(player: GPlayer, games: List<String> = this.games): InvFrame {
        val lang = player.lang(plugin)
        return InvFX.frame(3, Component.text(itemComponent.string("game-menu-title", lang))) {
            list(0, 0, 9, 3, true, { games }) {
                transform { itemComponent.item(it, lang) }
                onClickItem { _, _, item, event ->
                    event.isCancelled = true
                    player.closeInventory()
                    plugin.gameRegister.join(player, item.first)
                }
            }
        }.apply { player.openFrame(this) }
    }

}