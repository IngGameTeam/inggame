package io.github.inggameteam.inggame.component.view.selector

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.model.ItemStackModel
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.model.ModelView
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ItemStackPropSelector(
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, Editor, EditorView<Any> by editorView {

    private fun getItem(): ItemStackModel = ((get() as? ItemStackModel)?: ItemStackModel(null))

    enum class ItemField(val select: (ItemStackPropSelector, Player) -> Unit) {
        DISPLAY_NAME({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getItem().setName(it); view.parentSelector?.open(player)},
                {view.getItem().getItemStack().itemMeta?.displayName}))
                .open(player)
        }),
        LORE({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getItem().setLore(it); view.parentSelector?.open(player) },
                { view.getItem().getItemStack().itemMeta?.lore?.joinToString("\n") }))
                .open(player)

        }),
        ITEM({ view, player ->
            ItemStackEditor(EditorViewImp(view,
                { view.getItem().setItem(it as ItemStack); view.parentSelector?.open(player) },
                { view.getItem().getItemStack() }), view)
        }),
        GET_ITEM({ view, player ->
            player.inventory.addItem(view.getItem().getItemStack())
        })
    }

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<ItemField> = ItemField.values().toList()

    override fun select(t: Any, event: InventoryClickEvent) {
        (t as ItemField).select(this, event.whoClicked as Player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super.gui(gui)
        gui.slot(4, 2, getItem().getItemStack()) {
            val itemStack = getItem().getItemStack()
            if (itemStack.type !== Material.AIR) {
                (it.whoClicked as Player).inventory.addItem(itemStack)
            }
        }
    }

    override fun transform(t: Any): ItemStack {
        val itemField = t as ItemField
        return createItem(Material.OAK_PLANKS, itemField.name)
    }


}