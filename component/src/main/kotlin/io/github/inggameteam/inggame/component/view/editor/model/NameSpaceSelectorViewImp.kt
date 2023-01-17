package io.github.inggameteam.inggame.component.view.editor.model

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.selectors.Selector
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.koin.core.Koin

data class NameSpaceSelectorViewImp(
    override val componentService: ComponentService,
    override val app: Koin, override val plugin: IngGamePlugin
) : NameSpaceSelectorView, Selector<NameSpace> {
    override val elements: Collection<NameSpace> get() = componentService.getAll()
    override fun select(t: NameSpace, event: InventoryClickEvent) {
        ElementViewImp(this, t).open(event.whoClicked as Player)
    }

    override fun transform(t: NameSpace): ItemStack =
        createItem(
            Material.STONE,
            t.name.toString(),
            listOf(
                "${ChatColor.GREEN}" + t.parents.joinToString("${ChatColor.GRAY}, ${ChatColor.GREEN}"),
                t.elements.map { "${ChatColor.AQUA}${it.key}${ChatColor.GRAY}=${ChatColor.WHITE}${it.value}" }.joinToString("\n")
            ).joinToString("\n")
        )
}