package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.SubClassRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.Model
import io.github.inggameteam.inggame.component.view.entity.ModelView
import io.github.inggameteam.inggame.component.view.entity.ModelViewImp
import io.github.inggameteam.inggame.utils.singleClass
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
        app.get<SubClassRegistry>().getSubs(model.singleClass.kotlin)
            .filter { !it.java.isInterface }
            .sortedWith(Comparator { o1, o2 -> o1.simpleName!!.compareTo(o2.simpleName!!) })
            .map { it.createType() }

    override fun select(t: Model, event: InventoryClickEvent) {
        ModelFieldSelector(editorView, ModelViewImp(this, t), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Model) = createItem(Material.GOLD_BLOCK, t.singleClass.simpleName)

}