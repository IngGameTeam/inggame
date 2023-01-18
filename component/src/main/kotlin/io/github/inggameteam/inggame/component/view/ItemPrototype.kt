package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.utils.ColorUtil.color
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun createItem(
    type: Material,
    name: String? = null,
    lore: String? = null,
) = ItemStack(type).apply {
    itemMeta = Bukkit.getItemFactory().getItemMeta(type)!!.apply {
        setDisplayName(name?.color)
        if (lore !== null) this.lore = listOf(lore.color)
        amount = -128
    }
}