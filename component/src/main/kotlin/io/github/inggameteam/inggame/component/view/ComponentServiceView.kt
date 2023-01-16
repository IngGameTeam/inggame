package io.github.inggameteam.inggame.component.view

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.koin.core.Koin
import org.koin.core.qualifier.named






fun Collection<Any>.withBlank() = run { ArrayList<Any>(this) }.apply { repeat(92 - this.size) { add("Unit") } }.toMutableList()

fun nsSelector(app: Koin, componentService: ComponentService, plugin: IngGamePlugin) = run {
    val view = app.get<ComponentService>(named("view"))
    val selector = "ns-selector"
    Gui.frame(plugin, 6, view[selector, "selector-title", String::class])
        .list(0, 0, 9, 5, { componentService.getAll().withBlank() }, { ns ->
            if (ns is NameSpace) ItemStack(Material.STONE).apply {
                itemMeta = Bukkit.getItemFactory().getItemMeta(type)!!.apply {
                    setDisplayName(ns.name.toString())
                    lore = listOf(
                        "$GREEN" + ns.parents.joinToString("$GRAY, $GREEN"),
                        ns.elements.map { "${AQUA}${it.key}${GRAY}=${WHITE}${it.value}" }.joinToString("\n")
                    )
                }

            } else ItemStack(Material.BEDROCK)}) { list, gui ->
            gui.slot(0, 5) { event -> list.setIndex(list.index - 45) }
            gui.slot(8, 5) { event -> list.setIndex(list.index + 45) }
            list.onClick { x, y, pair, event ->
                val nameSpace = pair.second
                if (nameSpace is NameSpace) {
                    elSelector(app, componentService, nameSpace, plugin).openInventory(event.whoClicked as Player)
                }
            }
        }
}

fun elSelector(app: Koin, componentService: ComponentService, nameSpace: NameSpace, plugin: IngGamePlugin) = run {
    val view = app.get<ComponentService>(named("view"))
    val selector = "el-selector"
    Gui.frame(plugin, 6, view[selector, "selector-title", String::class].format(nameSpace.name))
        .list(0, 0, 9, 5, { nameSpace.elements.entries.withBlank()}, { ns ->
            if (ns is Pair<*, *>) {
                ItemStack(Material.DIRT).apply {
                    itemMeta = Bukkit.getItemFactory().getItemMeta(type)!!.apply {
                        setDisplayName("$GOLD" + ns.first.toString())
                        lore = listOf("$GREEN" + ns.second.toString())
                    }
                }
            } else ItemStack(Material.AIR)
        }) { list, gui ->
            gui.slot(0, 5) { event -> list.setIndex(list.index - 45) }
            gui.slot(8, 5) { event -> list.setIndex(list.index + 45) }
            list.onClick { x, y, pair, event ->
                println(pair.second)
                val element = pair.second
                if (element is Pair<*, *>) {
                    elEditor(app, componentService, nameSpace, element.first!!, plugin)
                }
            }
        }
}

fun elEditor(app: Koin, componentService: ComponentService, nameSpace: NameSpace, elem: Any, plugin: IngGamePlugin) {

}