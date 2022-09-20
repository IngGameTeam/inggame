package io.github.inggameteam.item.api

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

interface Item : Listener, PluginHolder<AlertPlugin>, ItemComponentGetter {
    val name: String

    fun nameOrNull(player: GPlayer, itemStack: ItemStack?): String? {
        if (itemStack === null) return null
        val lang = player.lang(plugin)
        val items = itemComp.stringListOrNull(name, lang)?: return null
        return items.firstOrNull { itemStack.isSimilar(itemComp.item(it, lang)) }
    }

    fun use(name: String, player: GPlayer)

}
