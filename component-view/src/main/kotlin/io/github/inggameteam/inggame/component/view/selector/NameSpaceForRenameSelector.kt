package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.EditorViewImp
import io.github.inggameteam.inggame.component.view.editor.StringEditor
import io.github.inggameteam.inggame.component.view.model.ComponentServiceView
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

class NameSpaceForRenameSelector(
    nameSpaceView: ComponentServiceView,
    override val parentSelector: Selector<*>? = null
) : ComponentServiceView by nameSpaceView, Selector<NameSpace> {
    override val elements: Collection<NameSpace> get() = componentService.getAll()


    override fun select(t: NameSpace, event: InventoryClickEvent) {
        StringEditor(EditorViewImp(this,
            {
                if (t.name == it) return@EditorViewImp
                componentService.removeNameSpace(t)
                componentService.addNameSpace(it)
                componentService[it].apply {
                    this.parents = t.parents
                    this.elements.putAll(t.elements)
                }
            },
            {t.name.toString()}), this)
            .open(player)
    }

    override fun transform(t: NameSpace) = createItem(Material.DIRT, "${ChatColor.RED}${t.name}")

}