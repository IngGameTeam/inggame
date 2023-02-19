package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ElementViewImp
import io.github.inggameteam.inggame.component.view.entity.NameSpaceView
import net.md_5.bungee.api.ChatColor.YELLOW
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class ElementForAddSelector(
    nameSpaceView: NameSpaceView,
    override val parentSelector: Selector<*>? = null
) : NameSpaceView by nameSpaceView, Selector<String> {
    override val elements: Collection<String> get() = app.get<PropertyRegistry>().getAllProp().filter {
        val name = it.name
        val clazzName = it.clazz.simpleName!!
        (name == nameSpace.name
                || clazzName == nameSpace.name
                || clazzName == nameSpace.name.toString()
                || nameSpace.parents.contains(clazzName)
        ) && (!nameSpace.elements.containsKey(name) && !nameSpace.elements.containsKey(clazzName))
    }.map { it.name }

    override fun select(t: String, event: InventoryClickEvent) {
        val prop = app.get<PropertyRegistry>().getProp(t)
        app.get<EditorRegistry>().getEditor(prop.type, ElementViewImp(this, Pair(t, Unit)), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: String) = createItem(Material.DIRT, "${YELLOW}$t")

}