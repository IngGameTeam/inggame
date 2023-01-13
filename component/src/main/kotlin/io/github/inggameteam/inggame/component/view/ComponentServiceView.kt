package io.github.inggameteam.inggame.component.view

import io.github.bruce0203.gui.Gui
import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.koin.core.Koin
import org.koin.core.qualifier.named

fun nsSelector(app: Koin, componentService: ComponentService, plugin: IngGamePlugin) = run {
    val view = app.get<ComponentService>(named("view"))
    val selector = "ns-selector"
    Gui.frame(plugin, 6, view[selector, "selector-title", String::class])
        .list(0, 0, 9, 5, { componentService.getAll().run { ArrayList<Any>(this) }
            .apply { repeat(45) { add(Unit) } }.toMutableList() }, { ns ->
            if (ns is NameSpace) ItemStack(Material.STONE).apply {
                itemMeta = Bukkit.getItemFactory().getItemMeta(type)!!.apply {
                    setDisplayName(ns.name.toString())
                    lore = listOf(
                        "$GREEN" + ns.parents.joinToString("$GRAY, $GREEN"),
                        ns.elements.map { "${AQUA}${it.key}${GRAY}=${WHITE}${it.value}" }.joinToString("\n")
                    )
                }

            } else ItemStack(Material.AIR)})
}