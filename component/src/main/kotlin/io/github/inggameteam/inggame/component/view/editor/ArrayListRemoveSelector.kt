package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.selector.AddButton
import io.github.inggameteam.inggame.component.view.selector.RemoveButton
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ArrayListRemoveSelector(
    private val modelView: ModelView,
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, ModelView by modelView, Editor {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<Any> = (editorView.get() as? ArrayList<*>)?: ArrayList()

    override fun select(t: Any, event: InventoryClickEvent) {
        (editorView.get.invoke() as ArrayList<*>).remove(t)
        parentSelector?.open(event.whoClicked as Player)
    }

    override fun transform(t: Any) = createItem(Material.GRAY_WOOL, "${ChatColor.RED}$t")


}