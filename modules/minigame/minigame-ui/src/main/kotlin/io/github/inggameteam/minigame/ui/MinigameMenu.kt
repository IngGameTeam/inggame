package io.github.inggameteam.minigame.ui

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.player.GPlayer
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component

interface MinigameMenu : PluginHolder<GamePlugin>, Game {

    fun minigameMenu(player: GPlayer, games: List<String> = plugin.gameSupplierRegister.keys.toList()) =
        InvFX.frame(3, Component.text(comp.string("minigame-menu-title", player.lang(plugin)))) {
            list(0, 0, 9, 3, true, { games }) {
                transform {
                    comp.item(it, player.lang(plugin))
                }
                onClickItem { _, _, item, event ->
                    event.isCancelled = true
                    player.closeInventory()
                    plugin.gameRegister.join(player, item.first)
                }
            }
        }.apply { player.openFrame(this) }

}