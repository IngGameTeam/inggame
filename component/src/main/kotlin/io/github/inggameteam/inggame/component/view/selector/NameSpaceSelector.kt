package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ComponentServiceView
import io.github.inggameteam.inggame.component.view.model.NameSpaceViewImp
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

data class NameSpaceSelector(
    val nameSpaceView: ComponentServiceView,
    override val parentSelector: Selector<*>? = null
) : ComponentServiceView by nameSpaceView, Selector<NameSpace> {
    override val elements: Collection<NameSpace> get() = componentService.getAll()

    override fun select(t: NameSpace, event: InventoryClickEvent) {
        ElementSelector(
            NameSpaceViewImp(this, t), this
        ).open(event.whoClicked as Player)
    }

    override fun transform(t: NameSpace): ItemStack =
        createItem(
            Material.STONE,
            t.name.toString(),
            listOf(
                "${ChatColor.GREEN}" + t.parents.joinToString("${ChatColor.GRAY}, ${ChatColor.GREEN}"),
                t.elements.map { "${ChatColor.AQUA}${it.key}${ChatColor.GRAY}=${ChatColor.WHITE}${it.value}" }
                    .joinToString("\n")
            ).joinToString("\n")
        )
}