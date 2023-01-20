package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.Gui
import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.OpenView
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.View
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

typealias EmptyElement = Unit

interface Selector<T : Any> : View, OpenView {

    val elements: Collection<T>

    val parentSelector: Selector<*>?

    fun transform(t: T): ItemStack

    fun select(t: T, event: InventoryClickEvent)

    fun gui(gui: GuiFrameDSL) = Unit

    val selector: String get() = javaClass.simpleName

    @Suppress("UNCHECKED_CAST")
    override fun open(player: Player) {
        Gui.frame(plugin, 6, view[selector, "selector-title", String::class])
            .list(0, 0, 9, 5, { elements.withBlank() },
                { ns -> try { if (ns is EmptyElement) throw AssertionError(); transform(ns as T)} catch (_: Throwable) {
                    ItemStack(Material.AIR)
                } }
            ) { list, gui ->
                gui.apply {
                    val prevItem = createItem(Material.FEATHER, view[selector, "previous-page", String::class])
                    val nextItem = createItem(Material.FEATHER, view[selector, "next-page", String::class])
                    slot(0, 5, prevItem) { list.setIndex(list.index - 45) }
                    slot(8, 5, nextItem) { list.setIndex(list.index + 45) }
                    if (parentSelector !== null) {
                        val backItem = createItem(Material.STRING, view[selector, "back-page", String::class])
                        slot(4, 5, backItem) { event -> parentSelector?.open(event.whoClicked as Player) }
                    }

                }
                gui(gui)
                list.onClick { _, _, pair, event ->
                    val t = try { pair.second as T } catch (_: Throwable) { return@onClick }
                    select(t, event)
                }
            }!!.openInventory(player)
    }

    private fun Collection<Any>.withBlank() = run { ArrayList<Any>(this) }.apply { repeat(45 - this.size) { add(EmptyElement) } }.toMutableList()


}