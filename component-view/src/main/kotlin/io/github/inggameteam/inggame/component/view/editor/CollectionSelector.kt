package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrameDSL
import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.selector.AddButton
import io.github.inggameteam.inggame.component.view.selector.RemoveButton
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.singleClass
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

@Suppress("UNCHECKED_CAST")
class CollectionSelector<T : Any>(
    private val editorView: EditorView<T>,
    override val parentSelector: Selector<*>? = null
): Selector<T>, Editor, EditorView<T> by editorView, AddButton<T>, RemoveButton<T> {
    override val previousSelector: Selector<*>? get() = parentSelector
    override val elements: Collection<T> = list as MutableCollection<T>

    private val genericType get() =
        ((editorView as ModelView).model.arguments[0].type!!)

    private val modelView = editorView as ModelView

    override fun addButton(player: Player) {
        elem(genericType, player, null)
    }

    private fun newE(collection: Collection<*>? = null): Any {
        return (editorView as ModelView).model.singleClass.getConstructor(Collection::class.java)
            .newInstance(collection?: ArrayList<Any>())
    }
    private val list get() = (editorView.get.invoke() as? MutableCollection<Any>)
        ?: (newE() as MutableCollection<Any>).apply {
            editorView.set.invoke(this as T)
        }

    private fun elem(genericType: KType, player: Player, t: Any?) {
        var e = t
        app.get<EditorRegistry>().getEditor(
            genericType, ModelViewImp(modelView, genericType), this,
            ModelEditorView(ModelViewImp(modelView, genericType), EditorViewImp(this,
                {
                    val l = list
                    val indexOf = l.indexOf(e)
                    if (indexOf != -1) {
                        e = it
                        editorView.set.invoke(newE(ArrayList(l).apply { set(indexOf, it) }) as T)
                    } else {
                        l.add(it)
                        editorView.set.invoke(l as T)
                        e = it
                    }
                },
                { e }))
        ).open(player)
    }

    override fun removeButton(player: Player) {
        CollectionRemoveSelector(editorView, this)
            .open(player)
    }

    override fun gui(gui: GuiFrameDSL) {
        super<RemoveButton>.gui(gui)
        super<AddButton>.gui(gui)
        super<Selector>.gui(gui)
    }

    override fun select(t: T, event: InventoryClickEvent) {
        elem(
            genericType,
            event.whoClicked as Player, t
        )
    }

    override fun transform(t: T) = createItem(Material.GRAY_WOOL, "${ChatColor.WHITE}$t")

}