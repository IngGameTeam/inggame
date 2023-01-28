package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.createItem
import org.bukkit.Material
import org.bukkit.entity.Player

interface RemoveButton<T : Any> : Selector<T> {

    fun removeButton(player: Player)

    override fun gui(gui: GuiFrameDSL) {
        super.gui(gui)
        gui.apply {
            val removeItem = createItem(Material.RED_DYE, selector.VIEW_REMOVE_BUTTON)
            slot(6, 5, removeItem) { event ->
                removeButton(event.whoClicked as Player)
            }
        }
    }

}