package io.github.inggameteam.item.game

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.item.api.ItemComponentGetter
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.utils.ItemUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.inventory.ItemStack

class ApplyShopItem(
    override val plugin: GamePlugin,
    private val purchase: PurchaseContainer,
    ) : PluginHolder<AlertPlugin>, HandleListener(plugin), ItemComponentGetter {

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        if (plugin.gameRegister.getJoinedGame(player).name != plugin.gameRegister.hubName) return
        val shopBlackList = itemComp.stringListOrNull("shop-item-black-list", plugin.defaultLanguage)?: listOf()
        val items = ArrayList(purchase[player].purchases.filterNot { shopBlackList.contains(it.name) })
            .apply { sortBy { it.lastTime }; reverse() }
        ItemSlot.values().forEach { it.resetSlot(player) }
        for (item in items) {
            val slot = itemComp.string(item.name + "-slot", plugin.defaultLanguage).run { ItemSlot.valueOf(this) }
            val itemStack = ItemUtil.safeClone(itemComp.item(item.name, player.lang(plugin)))
            itemStack.amount = item.amount
            slot.setItem(player, itemStack)
        }
    }

}

enum class ItemSlot(val size: Int, vararg slot: Int) {
    NONE(-1),
    HELMET(1, 39),
    CHEST_PLATE(1, 38),
    LEGGINGS(1, 37),
    BOOTS(1, 36),
    BAR(6, 1, 2, 3, 5, 6, 7);

    private var slots: IntArray = slot
    fun resetSlot(player: Player) = slots.forEach { player.inventory.setItem(it, null) }
    fun setItem(player: Player, itemStack: ItemStack) {
        val inventory = player.inventory
        val slot = slots.firstOrNull { inventory.getItem(it) === null }?: return
        player.inventory.setItem(slot, itemStack)
    }
}
