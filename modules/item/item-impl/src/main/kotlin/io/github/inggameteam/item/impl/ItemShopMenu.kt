package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.item.impl.ItemType.*
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ItemUtil.appendLore
import io.github.inggameteam.utils.ItemUtil.safeClone
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component

class ItemShopMenu(
    override val plugin: AlertPlugin,
    private val user: UserContainer,
    private val purchase: PurchaseContainer
) :
    Interact, Drop, InventoryClick, HandleListener(plugin) {
    override val name get() = "item-shop"
    override fun use(name: String, player: GPlayer) {
        shopMenu(player)
    }

    private fun toItemType(name: String) = valueOf(itemComp.string(name, plugin.defaultLanguage))


    private fun shopMenu(player: GPlayer) {
        val before = System.currentTimeMillis()
        val lang = player.lang(plugin)
        val inventory = itemComp.inventory("shop", lang)
        val rowSize = 6
        InvFX.frame(rowSize, Component.text(itemComp.string("shop-inventory-title", lang))) {
            val contents = inventory.contents
            val items = itemComp.stringList("shop", lang).map { Pair(it, safeClone(itemComp.item(it, lang))) }
                .filter { toItemType(it.first) !== LIMITED }.toMap()
            val pointBalanceItem = safeClone(itemComp.item("point-balance", lang))
            val pointBalanceIndex = contents.indexOf(pointBalanceItem)
            if (pointBalanceIndex != -1) {
                appendLore(pointBalanceItem,
                    itemComp.string("point-balance", lang).format(user[player].point), index = 0)
                contents[pointBalanceIndex] = pointBalanceItem
            }
            items.forEach { (name, item) ->
                val playerPurchase = purchase[player]
                val index = contents.indexOf(item)
                if (index == -1) return@forEach
                val amount = playerPurchase[name].amount
                contents[index] = null
                slot(index % 9, index / 9) {
                    when(toItemType(name)) {
                        VOLUME -> {
                            if (amount < 1) appendLore(item, "&7${itemComp.int(name)} 포인트")
                            else appendLore(item, listOf("&7${amount}개 구매함"))
                        }
                        TOGGLE -> {
                            if (amount < 1) appendLore(item, "&7${itemComp.int(name)} 포인트");
                            else if (amount < 2) appendLore(item, "&a착용 중")
                            else if (amount < 3) appendLore(item, "&c착용 중이 아님")
                        }
                    }
                    this@slot.item = item
                    onClick { event ->
                        items.toList().firstOrNull { it.second.isSimilar(event.currentItem) }?.apply {
                            itemComp.alertOrNull(first, lang)?.send(player)
                            val point = user[player].point
                            val itemPoint = itemComp.int(name)
                            when(toItemType(name)) {
                                VOLUME -> {
                                    if (amount < 64) {
                                        if (itemPoint <= point) {
                                            playerPurchase[name].amount = amount + 1
                                            user[player].point -= itemPoint
                                        } else playerPurchase[name].updateLastTime()
                                    } else playerPurchase[name].updateLastTime()
                                }
                                TOGGLE -> {
                                    if (amount < 1) {
                                        if (itemPoint <= point) {
                                            playerPurchase[name].amount = 1
                                            user[player].point -= itemPoint
                                        } else playerPurchase[name].updateLastTime()
                                    } else {
                                        if (amount < 2) playerPurchase[name].amount = 2
                                        else playerPurchase[name].amount = 1
                                    }
                                }
                            }
                            shopMenu(player)
                        }
                    }
                }
            }

            for (x in 0 until 9) for (y in 0 until rowSize) {
                val index = x + y * 9
                if (contents[index] !== null)
                    slot(x, y) {
                        item = contents[index]
                    }
            }
        }.apply { player.openFrame(this) }
        println("${System.currentTimeMillis() - before}")
    }

}