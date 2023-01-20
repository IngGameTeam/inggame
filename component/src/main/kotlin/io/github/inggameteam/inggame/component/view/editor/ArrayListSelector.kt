package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.selector.AddButton
import io.github.inggameteam.inggame.component.view.selector.RemoveButton
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.full.createType

@Suppress("UNCHECKED_CAST")
class ArrayListSelector(
    private val modelView: ModelView,
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, Editor, EditorView<Any> by editorView, AddButton<Any>, RemoveButton<Any> {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<Any> = (get() as? ArrayList<*>)?: ArrayList()

    override fun addButton(player: Player) {
        var e: Any? = null
        app.get<EditorRegistry>().getEditor(
            modelView.element.second.javaClass.kotlin.createType(),
            modelView, parentSelector, EditorViewImp(modelView, { e = it
                (editorView.get.invoke() as ArrayList<Any>).add(e!!) }, { e })
        ).open(player)
    }

    override fun removeButton(player: Player) {
        ArrayListRemoveSelector(modelView, editorView, this)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
    }

    override fun select(t: Any, event: InventoryClickEvent) {
        var e = t
        app.get<EditorRegistry>().getEditor(
            modelView.element.second.javaClass.kotlin.createType(),
            modelView, parentSelector, EditorViewImp(modelView, { e = it
                (editorView.get.invoke() as ArrayList<Any>).add(e) }, { e })
        ).open(event.whoClicked as Player)
    }

    override fun transform(t: Any) = createItem(Material.DIRT, "${ChatColor.WHITE}$t")

}