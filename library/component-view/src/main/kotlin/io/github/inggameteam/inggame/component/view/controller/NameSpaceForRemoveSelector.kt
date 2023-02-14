package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ComponentServiceView
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

class NameSpaceForRemoveSelector(
    nameSpaceView: ComponentServiceView,
    override val parentSelector: Selector<*>? = null
) : ComponentServiceView by nameSpaceView, Selector<NameSpace> {
    override val elements: Collection<NameSpace> get() = componentService.getAll()


    override fun select(t: NameSpace, event: InventoryClickEvent) {
        componentService.removeNameSpace(t.name)
    }

    override fun transform(t: NameSpace) = createItem(Material.DIRT, "${ChatColor.RED}${t.name}")

}