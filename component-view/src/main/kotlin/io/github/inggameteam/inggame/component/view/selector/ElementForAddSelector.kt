package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ElementViewImp
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import org.bukkit.ChatColor.YELLOW
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ElementForAddSelector(
    nameSpaceView: NameSpaceView,
    override val parentSelector: Selector<*>? = null
) : NameSpaceView by nameSpaceView, Selector<String> {
    override val elements: Collection<String> get() = app.get<PropertyRegistry>().getAllProp().filter {
        val notContains = nameSpace.elements.contains(it.name)
        notContains && it.name == nameSpace.name
                || notContains && it.clazz.simpleName!! == nameSpace.name.toString()
                || notContains && nameSpace.parents.contains(it.name)
                || notContains && nameSpace.parents.contains(it.clazz.simpleName!!)
    }.map { it.name }

    override fun select(t: String, event: InventoryClickEvent) {
        val prop = app.get<PropertyRegistry>().getProp(t)
        app.get<EditorRegistry>().getEditor(prop.type, ElementViewImp(this, Pair(t, Unit)), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: String) = createItem(Material.DIRT, "${YELLOW}$t")

}