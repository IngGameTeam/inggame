package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

interface Item : PluginHolder<AlertPlugin>, Listener {
    val name: String
    val itemComponent get() = plugin.components["item"]

    fun nameOrNull(player: GPlayer, itemStack: ItemStack?): String? {
        if (itemStack === null) return null
        val lang = player.lang(plugin)
        val items = itemComponent.stringListOrNull(name, lang)?: return null
        return items.firstOrNull { itemStack.isSimilar(itemComponent.item(it, lang)) }
    }

}
