package io.github.inggameteam.inggame.component.view.editor.selectors

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.model.View
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface Selector<T : Any> : View {

    val elements: Collection<T>

    fun transform(t: T): ItemStack

    fun select(t: T, event: InventoryClickEvent)

    @Suppress("UNCHECKED_CAST")
    fun open(player: Player) = run {
        Gui.frame(plugin, 6, view[selector, "selector-title", String::class])
            .list(0, 0, 9, 5, { elements.withBlank() }, { ns ->
                try { transform(ns as T)} catch (_: Throwable) { ItemStack(Material.AIR) }
            }) { list, gui ->
                gui.slot(0, 5,
                    createItem(Material.FEATHER, view[selector, "previous-page", String::class])
                ) { list.setIndex(list.index - 45) }
                gui.slot(8, 5,
                    createItem(Material.FEATHER, view[selector, "next-page", String::class])
                ) { list.setIndex(list.index + 45) }
                list.onClick { x, y, pair, event ->
                    val nameSpace = pair.second
                    try {
                        select(nameSpace as T, event)
                    } catch (_: Throwable) {}
                }
            }
}!!

    private fun Collection<Any>.withBlank() = run { ArrayList<Any>(this) }.apply { repeat(45 - this.size) { add("Unit") } }.toMutableList()


}