package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ElementViewImp
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

typealias Element = Pair<Any, Any>
class ElementSelector(nameSpaceView: NameSpaceView, override val parentSelector: Selector<*>? = null)
    : NameSpaceView by nameSpaceView, Selector<Element>, AddButton<Element>, RemoveButton<Element> {

    override fun addButton(player: Player) {
        ElementForAddSelector(this, this).open(player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
    }

    override fun removeButton(player: Player) {
        ElementForRemoveSelector(this, this).open(player)
    }

    override val elements: Collection<Pair<Any, Any>> get() = nameSpace.elements.map { Pair(it.key, it.value) }

    override fun select(t: Pair<Any, Any>, event: InventoryClickEvent) {
        val prop = app.get<PropertyRegistry>().getProp(t.first.toString())
        app.get<EditorRegistry>().getEditor(prop.type, ElementViewImp(this, t), this)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Pair<Any, Any>) =
        createItem(
            Material.DIRT,
            "${ChatColor.GREEN}" + t.first.toString(),
            listOf("${ChatColor.GOLD}" + t.second.toString()).joinToString("\n")
        )

}