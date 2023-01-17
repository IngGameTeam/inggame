package io.github.inggameteam.inggame.component.view.editor.model

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.selectors.Selector
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.koin.core.Koin

typealias Element = Pair<Any, Any>
data class ElementViewImp(
    override val componentService: ComponentService,
    override val app: Koin,
    override val nameSpace: NameSpace, override val plugin: IngGamePlugin
) : ElementView {

    override var parentSelector: Selector<*>? = null

    constructor(csEditor: NameSpaceSelectorView, nameSpace: NameSpace)
            : this(csEditor.componentService, csEditor.app, nameSpace, csEditor.plugin) {
        parentSelector = csEditor
        println(parentSelector)
    }

    override val elements: Collection<Pair<Any, Any>> get() = nameSpace.elements.map { Pair(it.key, it.value) }

    override fun select(t: Pair<Any, Any>, event: InventoryClickEvent) {
        val propertyRegistry = app.get<PropertyRegistry>()
        val propClass = propertyRegistry.getProp(t.first.toString())
        println(propClass)
    }

    override fun transform(t: Pair<Any, Any>) =
        createItem(
            Material.DIRT,
            "${ChatColor.GREEN}" + t.first.toString(),
            listOf("${ChatColor.GOLD}" + t.second.toString()).joinToString("\n")
        )

}