package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.utils.ColorUtil.colored
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun createItem(
    type: Material,
    name: String? = null,
    lore: String? = null,
) = ItemStack(type).apply {
    itemMeta = Bukkit.getItemFactory().getItemMeta(type)!!.apply {
        setDisplayName(name?.colored)
        if (lore !== null) this.lore = lore.colored.split("\n")
    }
}