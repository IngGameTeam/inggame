package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.Subs
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.Editor
import io.github.inggameteam.inggame.component.view.model.Model
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class SubTypeSelector(
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Model>, Editor {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<Model> get() = modelView.model.java.getAnnotation(Subs::class.java)
        ?.run { kClasses.toList() }?: throw AssertionError("an error occurred while read Subs")

    override fun select(t: Model, event: InventoryClickEvent) {
        ModelFieldSelector(ModelViewImp(this, t), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Model) = createItem(Material.GOLD_BLOCK, t.simpleName)

}