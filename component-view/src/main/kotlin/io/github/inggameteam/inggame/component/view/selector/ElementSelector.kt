package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.CollectionSelector
import io.github.inggameteam.inggame.component.view.editor.EditorViewImp
import io.github.inggameteam.inggame.component.view.editor.ModelEditorView
import io.github.inggameteam.inggame.component.view.model.ElementViewImp
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import io.github.inggameteam.inggame.component.view.singleClass
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.full.createType
import kotlin.reflect.full.starProjectedType

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
        gui.apply {
            val parentItem = createItem(Material.PURPLE_DYE, selector.VIEW_PARENT_BUTTON)
            slot(7, 5, parentItem) { event ->
                parentButton(event.whoClicked as Player)
            }
        }
    }

    private fun parentButton(player: Player) {
        val arguments = ArrayList<String>()::class.createType().arguments
        println(arguments)
        CollectionSelector(ModelEditorView(ModelViewImp(ElementViewImp(this, Pair(Unit, Unit)),
            NameSpace::parents.returnType.singleClass.kotlin.createType(arguments)), EditorViewImp(this,
            { componentService.setParents(nameSpace, it) },
            { componentService.getParents(nameSpace) })), this)
            .open(player)
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