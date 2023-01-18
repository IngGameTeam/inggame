package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class NameSpaceParentsSelector(
    nameSpaceView: NameSpaceView,
    override val parentSelector: Selector<*>? = null
) : Selector<NameSpace>, NameSpaceView by nameSpaceView {
    override val elements: Collection<NameSpace> get() = componentService.getAll()

    override fun transform(t: NameSpace): ItemStack {
        TODO("Not yet implemented")
    }

    override fun select(t: NameSpace, event: InventoryClickEvent) {
        TODO("Not yet implemented")
    }

}