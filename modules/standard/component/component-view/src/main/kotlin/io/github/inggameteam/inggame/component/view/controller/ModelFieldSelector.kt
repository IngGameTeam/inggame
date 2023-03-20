package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.entity.ModelView
import io.github.inggameteam.inggame.component.view.entity.ModelViewImp
import io.github.inggameteam.inggame.utils.singleClass
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

typealias Field = KProperty<*>

class ModelFieldSelector(
    private val editorView: EditorView<*>,
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Field>, SelectorImp<Field>(), Editor {

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<Field>
        get() = model.singleClass.kotlin. declaredMemberProperties
            .filter { it.javaField?.getAnnotation(BsonIgnore::class.java) === null }
            .filter { it.visibility == KVisibility.PUBLIC }
            .apply { getOrNewInstance() }

    @Suppress("DEPRECATION")
    private fun getOrNewInstance() =
        try { editorView.get()!! } catch (_: Throwable) { model.singleClass.newInstance().apply { set(this) } }

    private fun set(value: Any) {
        (editorView as EditorView<Any>).set.invoke(value)
    }

    override fun select(t: Field, event: InventoryClickEvent) {
        val type = try { t.returnType.apply { singleClass } } catch (_: Throwable) { model.arguments[0].type!! }
        val mView = ModelViewImp(this, type)
        app.get<EditorRegistry>().getEditor(
            type, this, this,
//            FieldEditorImp<Any>(FieldViewImp(this, t))
            ModelEditorView(
                mView, EditorViewImp(mView,
                { getOrNewInstance().apply { (t as KMutableProperty<*>).setter.call(this, it); set(this) } },
                { getOrNewInstance().run { t.getter.call(this).also { set(this) } } }))
        )
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Field) = createItem(Material.OAK_PLANKS, t.name, run {
        try {
            "${ChatColor.WHITE}" + (editorView.get()?.run { t.getter.call(this).toString() }?: "")
        } catch (_: Throwable) { "" }
    })

}