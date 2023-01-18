package io.github.inggameteam.inggame.component.view

import io.github.bruce0203.gui.GuiFrameDSL
import org.bukkit.Material
import org.bukkit.entity.Player

interface VarianceSelector<T : Any> : Selector<T> {

    fun add(player: Player)

    fun remove(player: Player)

    override fun gui(gui: GuiFrameDSL) {
        super.gui(gui)
        gui.apply {
            val addItem = createItem(Material.LIME_DYE, view[selector, "add-button", String::class])
            slot(5, 5, addItem) { event ->
                add(event.whoClicked as Player)
            }
            val removeItem = createItem(Material.LIME_DYE, view[selector, "remove-button", String::class])
            slot(6, 5, addItem) { event ->
                remove(event.whoClicked as Player)
            }
        }
    }

}