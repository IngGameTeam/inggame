package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.koin.core.Koin

data class NameSpaceParentsViewImp(
    override val componentService: ComponentService,
    override val app: Koin,
    override val nameSpace: NameSpace, override val plugin: IngGamePlugin
) : NameSpaceParentsView {
    override val elements: Collection<NameSpace>
        get() = TODO("Not yet implemented")
    override val parentSelector: Selector<*>?
        get() = TODO("Not yet implemented")

    override fun transform(t: NameSpace): ItemStack {
        TODO("Not yet implemented")
    }

    override fun select(t: NameSpace, event: InventoryClickEvent) {
        TODO("Not yet implemented")
    }
}