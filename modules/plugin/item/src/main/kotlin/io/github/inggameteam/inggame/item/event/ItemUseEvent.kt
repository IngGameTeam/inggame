package io.github.inggameteam.inggame.item.event

import io.github.inggameteam.inggame.item.ItemUseType
import io.github.inggameteam.inggame.item.wrapper.Item
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class ItemUseEvent(
    val player: Player,
    val item: Item,
    val itemStack: ItemStack,
    val itemUseType: ItemUseType
) : Event() {
    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }

}
