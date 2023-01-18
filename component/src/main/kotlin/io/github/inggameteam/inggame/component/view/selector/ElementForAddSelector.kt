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
        println(it.name);
        componentService.has(nameSpace, it.name) || it.clazz.simpleName.apply(::println!!.lowercase() == nameSpace.name.toString().lowercase()
    }.map { it.name }


    override fun select(t: String, event: InventoryClickEvent) {
        app.get<EditorRegistry>().getEditor(t, ElementViewImp(this, Pair(t, Unit)), parentSelector)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: String) = createItem(Material.DIRT, "${YELLOW}$t")

}