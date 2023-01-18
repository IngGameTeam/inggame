package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.Editor
import io.github.inggameteam.inggame.component.view.model.ModelView
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties

typealias Field = KProperty<*>
class ModelFieldSelector(
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Field>, Editor {

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<Field>
        get() = model.declaredMemberProperties

    override fun select(t: Field, event: InventoryClickEvent) {
        app.get<EditorRegistry>().getEditor(t.returnType, this, parentSelector)
    }

    override fun transform(t: Field) = createItem(Material.OAK_PLANKS, t.name)

}