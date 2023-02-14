package io.github.inggameteam.inggame.component.view.controller

import de.tr7zw.nbtapi.NBTItem
import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.model.ItemModel
import io.github.inggameteam.inggame.component.model.ItemModel.Companion.toItemModel
import io.github.inggameteam.inggame.component.view.createItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ItemStackPropSelector(
    private val editorView: EditorView<Any>,
    override val parentSelector: Selector<*>? = null
) : Selector<Any>, Editor, EditorView<Any> by editorView {

    private fun getItem(): ItemModel = ((get() as? ItemModel)?: ItemModel(null))

    private fun set(itemModel: ItemModel) = set.invoke(itemModel)

    enum class ItemField(val select: (ItemStackPropSelector, Player) -> Unit) {
        DISPLAY_NAME({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getItem().apply { name(it) }.apply(view::set); view.open(player)},
                {view.getItem().itemStack.itemMeta?.displayName}))
                .open(player)
        }),
        LORE({ view, player ->
            StringEditor(EditorViewImp(view,
                { view.getItem().apply { lore(it) }.apply(view::set); view.open(player) },
                { view.getItem().itemStack.itemMeta?.lore?.joinToString("\n") }))
                .open(player)

        }),
        ITEM({ view, player ->
            try {
                ItemStackEditor(EditorViewImp(view,
                    { (it as ItemStack).apply { view.set(this.toItemModel())}; view.open(player) },
                    { view.getItem().itemStack }), view)
                    .open(player)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }),
        GET_ITEM({ view, player ->
            player.inventory.addItem(view.getItem().itemStack)
        }),
        NBT_TAG({ view, player ->
            MapEditor(EditorViewImp(view,
                {
                    val itemStack = view.getItem().itemStack
                    val nbtItem = NBTItem(itemStack)
                    it.forEach { (k, v) -> nbtItem.setObject(k, v) }
                },
                {
                    val nbtItem = NBTItem(view.getItem().itemStack)
                    nbtItem.keys.associateWith { nbtItem.getString(it) }
                }
            ), view)
                .open(player)
        }),
    }

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<ItemField> = ItemField.values().toList()

    override fun select(t: Any, event: InventoryClickEvent) {
        (t as ItemField).select(this, event.whoClicked as Player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super.gui(gui)
        gui.slot(4, 2, getItem().itemStack) {
            val itemStack = getItem().itemStack
            if (itemStack.type !== Material.AIR) {
                (it.whoClicked as Player).inventory.addItem(itemStack)
            }
        }
    }

    override fun transform(t: Any): ItemStack {
        val itemField = t as ItemField
        return createItem(Material.END_STONE, itemField.name)
    }


}