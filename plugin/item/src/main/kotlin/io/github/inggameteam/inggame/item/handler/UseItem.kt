package io.github.inggameteam.inggame.item.handler

import de.tr7zw.nbtapi.NBTItem
import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.item.ItemUseType
import io.github.inggameteam.inggame.item.component.ItemResource
import io.github.inggameteam.inggame.item.event.ItemUseEvent
import io.github.inggameteam.inggame.item.wrapper.Item
import io.github.inggameteam.inggame.item.wrapper.ItemImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.koin.ext.getFullName

class UseItem(
    private val itemResource: ItemResource,
    val plugin: IngGamePlugin
) : HandleListener(plugin) {

    private val nbtItemKey get() = this::class.simpleName

    private fun getItem(itemStack: ItemStack): Item? {
        if (itemStack.type === Material.AIR) return null
        val name = NBTItem(itemStack).getString(nbtItemKey)
        if (name === null) return null
        return itemResource[name, ::ItemImp]
    }

    private fun use(player: Player, itemStack: ItemStack, useType: ItemUseType) {
        plugin.server.pluginManager.callEvent(ItemUseEvent(player, getItem(itemStack)?: return, itemStack, useType))
    }

    @Suppress("unused")
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.hasItem()) {
            val item = event.item?: return
            use(event.player, item, ItemUseType.RIGHT_CLICK)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val player = event.damager
        if (player !is Player) return
        use(player, player.inventory.run { getItem(heldItemSlot) }?: return, ItemUseType.LEFT_CLICK)
    }

    @Suppress("unused")
    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val player = event.player
        use(player, event.itemDrop.itemStack, ItemUseType.DROP)
    }

    @Suppress("unused")
    @EventHandler
    fun onClickInventory(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player) return
        use(player, event.currentItem?: return, ItemUseType.INVENTORY_CLICK)
    }

}