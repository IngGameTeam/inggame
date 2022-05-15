package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.player.GPlayer
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component

class MinigameMenu(override val plugin: GamePlugin) : Interact, Drop, InventoryClick {
    override val name get() = "game-menu"
    override fun use(name: String, player: GPlayer) {
        minigameMenu(player)
    }

    fun minigameMenu(player: GPlayer, games: List<String> = plugin.gameSupplierRegister.keys.toList()) =
        InvFX.frame(3, Component.text(itemComponent.string("minigame-menu-title", player.lang(plugin)))) {
            list(0, 0, 9, 3, true, { games }) {
                transform {
                    itemComponent.item(it, player.lang(plugin))
                }
                onClickItem { _, _, item, event ->
                    event.isCancelled = true
                    player.closeInventory()
                    plugin.gameRegister.join(player, item.first)
                }
            }
        }.apply { player.openFrame(this) }

}