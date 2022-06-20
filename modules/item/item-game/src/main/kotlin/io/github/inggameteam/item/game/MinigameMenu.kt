package io.github.inggameteam.item.game

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.base.CLICK_PAUSED
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ItemUtil
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.frame.InvFrame
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Material

class MinigameMenu(
    override val plugin: GamePlugin,
    override val name: String = "game-menu",
) : Interact, Drop, InventoryClick, HandleListener(plugin) {

    override fun use(name: String, player: GPlayer) {
        if (player[CLICK_PAUSED] !== null) return
        if (plugin.gameRegister.getJoinedGame(player).name == plugin.gameRegister.hubName) minigameMenu(player)
    }

    private val games get() = plugin.gameSupplierRegister.keys.toList().filter { it != plugin.gameRegister.hubName }.toList()

    private fun minigameMenu(player: GPlayer): InvFrame {
        val lang = player.lang(plugin)
        val games = itemComp.stringList("$name-games", lang)
        return InvFX.frame(3, Component.text(itemComp.string("$name-title", lang))) {
            list(0, 0, 9, 3, true, { games }) {
                transform { itemComp.itemOrNull(it, lang)?: ItemUtil.itemStack(Material.STONE, it) }
                onClickItem { _, _, item, event ->
                    event.isCancelled = true
                    player.closeInventory()
                    plugin.gameRegister.join(player, item.first)
                }
            }
        }.apply { player.openFrame(this) }
    }

}