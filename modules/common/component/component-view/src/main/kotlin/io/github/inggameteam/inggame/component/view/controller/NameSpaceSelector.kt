package io.github.inggameteam.inggame.component.view.controller

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ComponentServiceView
import io.github.inggameteam.inggame.component.view.entity.NameSpaceViewImp
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

data class NameSpaceSelector(
    val nameSpaceView: ComponentServiceView,
    override val parentSelector: Selector<*>? = null
) : ComponentServiceView by nameSpaceView, Selector<NameSpace>, SelectorImp<NameSpace>(), AddButton<NameSpace>, RemoveButton<NameSpace> {
    override fun addButton(player: Player) {
        StringEditor(EditorViewImp(this,
            { componentService.addNameSpace(it) }, {""}), this)
            .open(player)
    }

    override fun removeButton(player: Player) {
        NameSpaceForRemoveSelector(this, this).open(player)
    }

    private fun renameButton(player: Player) {
        NameSpaceForRenameSelector(this, this)
            .open(player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<Selector>.gui(gui)
        super<AddButton>.gui(gui)
        super<RemoveButton>.gui(gui)
        gui.apply {
            val renameItem = createItem(Material.ORANGE_DYE, selector.VIEW_RENAME_BUTTON)
            slot(7, 5, renameItem) { event ->
                renameButton(event.whoClicked as Player)
            }
        }
    }

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
                "${ChatColor.GREEN}" + t.parents.joinToString("\n${ChatColor.GREEN}"),
                t.elements.map { "${ChatColor.AQUA}${it.key}${ChatColor.GRAY}=${ChatColor.WHITE}${it.value}" }
                    .joinToString("\n")
            ).joinToString("\n")
        )
}