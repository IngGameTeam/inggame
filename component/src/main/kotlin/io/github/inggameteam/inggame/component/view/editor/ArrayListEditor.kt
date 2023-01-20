package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.model.ElementViewImp
import io.github.inggameteam.inggame.component.view.selector.AddButton
import io.github.inggameteam.inggame.component.view.selector.RemoveButton
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ArrayListEditor(
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, Editor, EditorView<Any> by editorView, AddButton<Any>, RemoveButton<Any> {
    override val previousSelector: Selector<*>? get() = parentSelector
    override fun addButton(player: Player) {
        val prop = app.get<PropertyRegistry>().getProp(t)
        app.get<EditorRegistry>().getEditor(prop.type, , parentSelector, this)
            .open(player)
    }

    override fun removeButton(player: Player) {
        TODO("Not yet implemented")
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
    }

    override val elements: Collection<Any> = (get() as? ArrayList<*>)?: ArrayList()

    override fun select(t: Any, event: InventoryClickEvent) {

    }

    override fun transform(t: Any): ItemStack {
        TODO("Not yet implemented")
    }
}