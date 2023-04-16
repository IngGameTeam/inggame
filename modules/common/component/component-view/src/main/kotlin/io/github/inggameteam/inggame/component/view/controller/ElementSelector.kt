package io.github.inggameteam.inggame.component.view.controller

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ElementViewImp
import io.github.inggameteam.inggame.component.view.entity.ModelViewImp
import io.github.inggameteam.inggame.component.view.entity.NameSpaceView
import io.github.inggameteam.inggame.utils.singleClass
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.full.createType

typealias Element = Pair<Any, Any>
class ElementSelector(nameSpaceView: NameSpaceView, override val parentSelector: Selector<*>? = null)
    : NameSpaceView by nameSpaceView, Selector<Element>, SelectorImp<Element>(), AddButton<Element>, RemoveButton<Element> {

    @Deprecated("ornamental", ReplaceWith("stringGenericList"))
    val stringGenericList: ArrayList<String>
        get() = throw AssertionError()

    override fun addButton(player: Player) {
        ElementForAddSelector(this, this).open(player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
        gui.apply {
            val parentItem = createItem(Material.PURPLE_DYE, selector.VIEW_PARENT_BUTTON)
            slot(7, 5, parentItem) { event ->
                parentButton(event.whoClicked as Player)
            }
        }
    }
    @Suppress("DEPRECATION")
    private fun parentButton(player: Player) {
        CollectionSelector(ModelEditorView(
            ModelViewImp(
                ElementViewImp(this, Pair(Unit, Unit)),
            ::stringGenericList.returnType
        ), EditorViewImp(this,
            { componentService.setParents(nameSpace.name, it) },
            { componentService.getParents(nameSpace.name) })), this)
            .open(player)
    }

    override fun removeButton(player: Player) {
        ElementForRemoveSelector(this, this).open(player)
    }

    override val elements: Collection<Pair<Any, Any>> get() = nameSpace.elements.map { Pair(it.key, it.value) }

    override fun select(t: Pair<Any, Any>, event: InventoryClickEvent) {
        val prop = app.get<PropertyRegistry>().getProp(t.first.toString())
        val type = if (prop.type.singleClass.isInterface)
            try { componentService.find(nameSpace.name, t.first.toString(), Any::class)::class.createType() }
            catch(e: Exception) { prop.type }
        else prop.type
        app.get<EditorRegistry>().getEditor(type, ElementViewImp(this, t), this)
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Pair<Any, Any>) =
        createItem(
            Material.DIRT,
            "${ChatColor.GREEN}" + t.first.toString(),
            listOf("${ChatColor.GOLD}" + t.second.toString()).joinToString("\n")
        )

}