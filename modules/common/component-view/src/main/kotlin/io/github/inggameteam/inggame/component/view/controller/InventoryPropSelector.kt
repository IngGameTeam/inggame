package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.model.InventoryModel
import io.github.inggameteam.inggame.component.model.InventoryModel.Companion.toInventoryModel
import io.github.inggameteam.inggame.component.view.createItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class InventoryPropSelector(
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, Editor, EditorView<Any> by editorView {

    private fun getInv(): InventoryModel = ((get() as? InventoryModel)?: InventoryModel())

    private fun set(inventoryModel: InventoryModel) = set.invoke(inventoryModel)

    enum class InvField(val select: (InventoryPropSelector, Player) -> Unit) {
        TITLE({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getInv().apply { setTitle(it) }.apply(view::set); view.open(player)},
                {view.getInv().getTitle()}))
                .open(player)
        }),
        TYPE({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getInv().apply { setType(it) }.apply(view::set); view.open(player) },
                { view.getInv().getType() }))
                .open(player)
        }),
        SET_TO_MY_INVENTORY({ view, player ->
            view.set(player.openInventory.bottomInventory.toInventoryModel())
        }),
        FIND_CHEST({ view, player ->
            try {
                InventoryEditor(EditorViewImp(view,
                    { (it as Inventory).apply { view.set(this.toInventoryModel())}; view.open(player) },
                    { view.getInv().inventory }), view)
                    .open(player)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }),
        GET_INVENTORY({ view, player ->
            player.inventory.contents = view.getInv().inventory.contents
        }),
    }

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<InvField> = InvField.values().toList()

    override fun select(t: Any, event: InventoryClickEvent) {
        (t as InvField).select(this, event.whoClicked as Player)
    }

    override fun transform(t: Any): ItemStack {
        val invField = t as InvField
        return createItem(Material.END_STONE, invField.name)
    }

}