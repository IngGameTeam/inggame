package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.model.ComponentServiceEditor
import io.github.inggameteam.inggame.component.view.editor.model.ElementEditor
import io.github.inggameteam.inggame.component.view.editor.model.ElementEditorImp
import io.github.inggameteam.inggame.component.view.editor.model.NameSpaceEditor
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

fun Collection<Any>.withBlank() = run { ArrayList<Any>(this) }.apply { repeat(45 - this.size) { add("Unit") } }.toMutableList()

inline fun <reified T : Any> selector(csEditor: ComponentServiceEditor, elements: Collection<T>,
                                      crossinline transform: (T) -> ItemStack, crossinline editor: (InventoryClickEvent, T) -> Unit,
) = csEditor.run {
    Gui.frame(plugin, 6, view[selector, "selector-title", String::class])
        .list(0, 0, 9, 5, { elements.withBlank() }, { ns ->
            if (ns is T) transform(ns) else ItemStack(Material.BEDROCK)
        }) { list, gui ->
            gui.slot(0, 5,
                createItem(Material.FEATHER, view[selector, "previous-page", String::class])
            ) { list.setIndex(list.index - 45) }
            gui.slot(8, 5,
                createItem(Material.FEATHER, view[selector, "next-page", String::class])
            ) { list.setIndex(list.index + 45) }
            list.onClick { x, y, pair, event ->
                val nameSpace = pair.second
                if (nameSpace is T) {
                    editor(event, nameSpace)
                }
            }
        }
}

fun nsSelector(nsEditor: NameSpaceEditor) = selector(nsEditor, nsEditor.componentService.getAll(), { ns ->
    createItem(Material.STONE,
        ns.name.toString(),
        listOf(
            "${ChatColor.GREEN}" + ns.parents.joinToString("${ChatColor.GRAY}, ${ChatColor.GREEN}"),
            ns.elements.map { "${ChatColor.AQUA}${it.key}${ChatColor.GRAY}=${ChatColor.WHITE}${it.value}" }.joinToString("\n")
        ).joinToString("\n")
    )
}, { event, nameSpace ->
    elSelector(ElementEditorImp(nsEditor, nameSpace)).openInventory(event.whoClicked as Player)
})

fun elSelector(elEditor: ElementEditor) = selector(elEditor,
    elEditor.nameSpace.elements.entries.map { Pair(it.key, it.value) }, { pair ->
        createItem(
            Material.DIRT,
            "${ChatColor.GREEN}" + pair.first.toString(),
            listOf("${ChatColor.GOLD}" + pair.second.toString()).joinToString("\n")
        )
    }, { event, pair ->
        val propertyRegistry = elEditor.app.get<PropertyRegistry>()
        val propClass = propertyRegistry.getProp(pair.first.toString())
        println(propClass)
    })