package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.SubClassRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.Editor
import io.github.inggameteam.inggame.component.view.editor.EditorView
import io.github.inggameteam.inggame.component.view.model.Model
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.singleClass
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.full.createType

class SubTypeSelector(
    private val editorView: EditorView<*>,
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Model>, Editor {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<Model> get() =
        app.get<SubClassRegistry>().getSubs(model.singleClass.kotlin).map { it.createType() }

    override fun select(t: Model, event: InventoryClickEvent) {
        ModelFieldSelector(editorView, ModelViewImp(this, t), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Model) = createItem(Material.GOLD_BLOCK, t.singleClass.simpleName)

}