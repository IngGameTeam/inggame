package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Drop
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.item.api.InventoryClick
import io.github.inggameteam.item.impl.ItemType.*
import io.github.inggameteam.item.impl.event.PurchaseEvent
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ItemUtil.appendLore
import io.github.inggameteam.utils.ItemUtil.safeClone
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class ItemShopMenu(
    override val plugin: AlertPlugin,
    private val user: UserContainer,
    private val purchase: PurchaseContainer,
    override val name: String = "item-shop"
) :
    Interact, Drop, InventoryClick, HandleListener(plugin) {
    override fun use(name: String, player: GPlayer) {
        shopMenu(player)
    }



    private fun shopMenu(player: GPlayer) {
        val lang = player.lang(plugin)
        val inventory = itemComp.inventory(this.name, lang)
        val rowSize = 6
        InvFX.frame(rowSize, Component.text(itemComp.string("${this@ItemShopMenu.name}-inventory-title", lang))) {
            itemComp.itemOrNull("vote", player.lang(plugin))?.apply {
                slot(0, 5) {
                    item = this@apply
                    onClick {
                        itemComp.send("vote", player)
                    }
                }
            }
            itemComp.itemOrNull("discord", player.lang(plugin))?.apply {
                slot(1, 5) {
                    item = this@apply
                    onClick {
                        itemComp.send("discord", player)
                    }
                }
            }
            val items = itemComp.stringList(this@ItemShopMenu.name + "-shop-items", lang).map { Pair(it, safeClone(itemComp.item(it, lang))) }
                .filter { toItemType(it.first) !== LIMITED }.toMap()
            val pointBalanceItem = safeClone(itemComp.item("point-balance", lang))
            val pointBalanceIndex = inventory.indexOf(pointBalanceItem)
            if (pointBalanceIndex != -1) {
                slot (pointBalanceIndex % 9, pointBalanceIndex / 9) {
                    appendLore(pointBalanceItem,
                        itemComp.string("point-balance", lang).format(user[player].point), index = 0)
                    item = pointBalanceItem
                }
            }
            items.forEach { (name, item) ->
                val playerPurchase = purchase[player]
                val index = inventory.indexOf(item)
                if (index == -1) return@forEach
                val amount = playerPurchase[name].amount
                slot(index % 9, index / 9) {
                    when(toItemType(name)) {
                        VOLUME -> {
                            if (amount < 1) appendLore(item, "&7${itemComp.int(name)} 포인트")
                            else appendLore(item, listOf("&7${amount}개 구매함"))
                        }
                        TOGGLE -> {
                            if (amount < 1) appendLore(item, "&7${itemComp.int(name)} 포인트")
                            else if (amount < 2) appendLore(item, "&a착용 중")
                            else if (amount < 3) appendLore(item, "&c착용 중이 아님")
                        }
                        else -> {}
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
                                        }
                                    }
                                }
                                TOGGLE -> {
                                    if (amount < 1) {
                                        if (itemPoint <= point) {
                                            playerPurchase[name].amount = 1
                                            user[player].point -= itemPoint
                                        }
                                    } else {
                                        if (amount < 2) playerPurchase[name].amount = 2
                                        else playerPurchase[name].amount = 1
                                    }
                                }
                                else -> {}
                            }
                            playerPurchase[name].updateLastTime()
                            Bukkit.getPluginManager().callEvent(PurchaseEvent(player))
                            shopMenu(player)
                        }
                    }
                }
            }



        }.apply { player.openFrame(this) }
    }

}