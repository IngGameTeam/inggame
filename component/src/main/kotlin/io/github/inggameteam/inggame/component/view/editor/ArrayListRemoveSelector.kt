package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class ArrayListRemoveSelector<T: Any>(
    private val editorView: EditorView<T>,
    override val parentSelector: Selector<*>? = null
) : Selector<T>, EditorView<T> by editorView, Editor {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<T> = (editorView.get() as? ArrayList<T>)?: ArrayList()

    override fun select(t: T, event: InventoryClickEvent) {
        (editorView.get.invoke() as ArrayList<*>).remove(t)
        parentSelector?.open(event.whoClicked as Player)
    }

    override fun transform(t: T) = createItem(Material.GRAY_WOOL, "${ChatColor.RED}$t")


}