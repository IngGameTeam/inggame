package io.github.inggameteam.inggame.component.view.controller

import io.github.bruce0203.gui.Gui
import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.OpenView
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.View
import io.github.inggameteam.inggame.component.view.entity.ViewImp
import io.github.inggameteam.inggame.component.view.entity.createEmptyModelView
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

typealias EmptyElement = Unit

interface Selector<T : Any> : View, OpenView {

    val elements: Collection<T>

    var searchKey: String?

    val parentSelector: Selector<*>?

    val selector get() = getSelector(getSelectorName())

    fun getSelectorName(): String = javaClass.simpleName

    fun transform(t: T): ItemStack

    fun select(t: T, event: InventoryClickEvent)

    fun gui(gui: GuiFrameDSL) = Unit

    @Suppress("UNCHECKED_CAST")
    override fun open(player: Player) {
        Gui.frame(plugin, 6, selector.VIEW_TITLE)
            .list(0, 0, 9, 5, { elements.withBlank() },
                { ns -> try { if (ns is EmptyElement) throw AssertionError(); transform(ns as T)} catch (_: Throwable) {
                    ItemStack(Material.AIR)
                } }
            ) { list, gui ->
                gui.apply {
                    val prevItem = createItem(Material.FEATHER, selector.VIEW_PREV_PAGE)
                    val nextItem = createItem(Material.FEATHER, selector.VIEW_NEXT_PAGE)
                    slot(0, 5, prevItem) { list.setIndex(list.index - 45) }
                    slot(8, 5, nextItem) { list.setIndex(list.index + 45) }
                    if (parentSelector !== null) {
                        val backItem = createItem(Material.STRING, selector.VIEW_BACK_PAGE)
                        slot(4, 5, backItem) { event -> parentSelector?.open(event.whoClicked as Player) }
                    }

                }
                val searchItem = createItem(Material.COMPASS,
                    if (searchKey === null) selector.VIEW_SEARCH_BUTTON else selector.VIEW_CLEAR_SEARCH_BUTTON
                )
                gui.slot(2, 5, searchItem) { event ->
                    StringEditor(EditorViewImp(ViewImp(app, plugin, player),
                        { searchKey = it.ifEmpty { null }; open(player) },
                        { searchKey?: "" }))
                        .open(player)
                }
                gui(gui)
                list.onClick { _, _, pair, event ->
                    val t = try { pair.second as T } catch (_: Throwable) { return@onClick }
                    try { select(t, event) } catch (_: ClassCastException) {}
                }
            }!!.openInventory(player)
    }

    private fun Collection<Any>.withBlank() = run { ArrayList<Any>(this) }.apply { repeat(45 - this.size) { add(EmptyElement) } }.toMutableList()


}