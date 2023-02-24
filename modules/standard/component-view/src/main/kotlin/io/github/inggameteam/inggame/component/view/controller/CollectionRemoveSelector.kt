package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.createItem
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

@Suppress("UNCHECKED_CAST")
class CollectionRemoveSelector<T: Any>(
    private val editorView: EditorView<T>,
    override val parentSelector: Selector<*>? = null
) : Selector<T>, EditorView<T> by editorView, Editor {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<T> = (editorView.get() as? MutableCollection<T>)?: emptyList()

    override fun select(t: T, event: InventoryClickEvent) {

        editorView.set.invoke((editorView.get.invoke() as MutableCollection<*>).apply { remove(t) } as T)
        open(event.whoClicked as Player)
//        parentSelector?.open(event.whoClicked as Player)
    }

    override fun transform(t: T) = createItem(Material.GRAY_WOOL, "${ChatColor.RED}$t")


}