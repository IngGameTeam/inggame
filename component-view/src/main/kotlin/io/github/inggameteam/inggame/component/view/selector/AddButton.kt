package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.createItem
import org.bukkit.Material
import org.bukkit.entity.Player

interface AddButton<T : Any> : Selector<T> {

    fun addButton(player: Player)

    override fun gui(gui: GuiFrameDSL) {
        super.gui(gui)
        gui.apply {
            val addItem = createItem(Material.LIME_DYE, view[selector, "add-button", String::class])
            slot(5, 5, addItem) { event ->
                addButton(event.whoClicked as Player)
            }
        }
    }

}