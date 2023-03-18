package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ModelView
import io.github.inggameteam.inggame.utils.singleClass
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

@Suppress("UNCHECKED_CAST")
class EnumEditor(
    private val modelView: ModelView,
    editorView: EditorView<*>,
    override val parentSelector: Selector<*>?
) : Selector<Enum<*>>, SelectorImp<Enum<*>>(), Editor, EditorView<Enum<*>> by editorView as EditorView<Enum<*>> {
    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<Enum<*>> get() = modelView.model.singleClass.enumConstants.map { it as Enum<*> }

    override fun select(t: Enum<*>, event: InventoryClickEvent) {
        set(t)
        parentSelector?.open(event.whoClicked as Player)
    }

    override fun transform(t: Enum<*>) = createItem(Material.CYAN_WOOL, "${t.javaClass.simpleName}(${t.name})")

}