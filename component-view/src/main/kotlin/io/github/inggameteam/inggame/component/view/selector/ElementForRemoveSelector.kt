package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import org.bukkit.ChatColor.RED
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

class ElementForRemoveSelector(
    nameSpaceView: NameSpaceView,
    override val parentSelector: Selector<*>? = null
) : NameSpaceView by nameSpaceView, Selector<String> {
    override val elements: Collection<String> get() = nameSpace.elements.keys.map { it.toString() }


    override fun select(t: String, event: InventoryClickEvent) {
        componentService.set(nameSpace.name, t, null)
    }

    override fun transform(t: String) = createItem(Material.DIRT, "${RED}$t")

}