package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.selector.AddButton
import io.github.inggameteam.inggame.component.view.selector.RemoveButton
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

@Suppress("UNCHECKED_CAST")
class ArrayListSelector<T : Any>(
    private val editorView: EditorView<T>,
    override val parentSelector: Selector<*>? = null
): Selector<T>, Editor, EditorView<T> by editorView, AddButton<T>, RemoveButton<T> {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<T> = (get() as? ArrayList<T>)?: ArrayList()

    private val genericType get() =
        (((editorView as ModelView).model.javaType as ParameterizedType).actualTypeArguments[0] as Class<*>).kotlin.createType()

    private val modelView = editorView as ModelView

    override fun addButton(player: Player) {
        elem(genericType, player)
    }

    private val list get() = (editorView.get.invoke() as? ArrayList<Any>)
        ?: ArrayList<Any>().apply {
            editorView.set.invoke(this as T)
        }

    private fun elem(genericType: KType, player: Player, index: Int = list.size) {
        app.get<EditorRegistry>().getEditor(
            genericType, ModelViewImp(modelView, genericType), this,
            ModelEditorView(ModelViewImp(modelView, genericType), EditorViewImp(this,
                { if (index == list.size) list.add(it) else list[index] = it },
                { if (index == list.size) null else list[index] }))
        ).open(player)
    }

    override fun removeButton(player: Player) {
        ArrayListRemoveSelector(editorView, this)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
    }

    override fun select(t: T, event: InventoryClickEvent) {
        elem(
            t.javaClass.kotlin.starProjectedType,
            event.whoClicked as Player, list.indexOf(t)
        )
    }

    override fun transform(t: T) = createItem(Material.DIRT, "${ChatColor.WHITE}$t")

}