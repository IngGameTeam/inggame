package io.github.inggameteam.utils

import io.github.inggameteam.utils.ColorUtil.color
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemUtil {
    @JvmStatic
    fun safeClone(itemStack: ItemStack?) = itemStack!!.clone()
    @JvmStatic
    fun itemMeta(itemStack: ItemStack) =
        if (itemStack.hasItemMeta()) itemStack.itemMeta!! else itemMeta(itemStack.type)
    fun itemMeta(type: Material) = Bukkit.getItemFactory().getItemMeta(type)!!
    @JvmStatic
    fun itemStack(type: Material,
                  name: String? = null,
                  lore: List<String> = emptyList(),
                  amount: Int = 1
    ): ItemStack {
        return ItemStack(type).apply {
            itemMeta = itemMeta(type).apply {
                if (name != null) setDisplayName(name.color())
                if (lore.isNotEmpty()) setLore(lore.map {it.color()})
            }
            this.amount = amount
        }
    }
    @JvmStatic
    fun appendLore(itemStack: ItemStack, lore: List<String>) {
        val itemMeta = itemMeta(itemStack)
        if (itemMeta.hasLore()) itemMeta.lore!!.addAll(lore.map {it.color()})
        else itemMeta.lore = ArrayList(lore.map {it.color()})
        itemStack.itemMeta = itemMeta
    }
    @JvmStatic
    fun amount(itemStack: ItemStack, amount: Int) {
        itemStack.amount = amount
    }
}