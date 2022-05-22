package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ItemUtil
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component

class ItemShopMenu(override val plugin: AlertPlugin, private val users: UserContainer) :
    Interact, Drop, InventoryClick, HandleListener(plugin) {
    override val name get() = "item-shop"
    override fun use(name: String, player: GPlayer) {
        shopMenu(player)
    }

    private fun shopMenu(player: GPlayer) {
        val lang = player.lang(plugin)
        val inventory = itemComponent.inventory("shop", lang)
        val items = itemComponent.stringList("shop", lang).map { Pair(it, itemComponent.item(it, lang)) }
        val rowsize = 6
        InvFX.frame(rowsize, Component.text(itemComponent.string("shop-inventory-title", lang))) {
            val point = inventory.indexOf(itemComponent.item("point-balance", lang))
            for (x in 0 until 9) for (y in 0 until rowsize)
                slot(x, y) {
                    val index = x + y * 9
                    item = inventory.getItem(index)?.let {
                        if (index == point) {
                            ItemUtil.appendLore(it, listOf(itemComponent.string("point-balance", lang).format(users[player].point)), 0)
                        }
                        it
                    }
                    onClick { event ->
                        items.firstOrNull { it.second.isSimilar(event.currentItem) }?.apply {
                            itemComponent.alertOrNull(first, lang)?.send(player)
                            
                        }
                    }
                }
        }.apply { player.openFrame(this) }
    }

}