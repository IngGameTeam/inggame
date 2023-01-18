package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

typealias Element = Pair<Any, Any>
class ElementSelector(nameSpaceView: NameSpaceView, override val parentSelector: Selector<*>? = null)
    : NameSpaceView by nameSpaceView, Selector<Element> {

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