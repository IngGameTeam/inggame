package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.model.FieldViewImp
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.singleClass
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

typealias Field = KProperty<*>
class ModelFieldSelector(
    private val editorView: EditorView<*>,
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Field>, Editor {

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<Field>
        get() = model.singleClass.kotlin. declaredMemberProperties
            .filter { it.javaField?.getAnnotation(BsonIgnore::class.java) === null }

    @Suppress("DEPRECATION", "UNCHECKED_CAST")
    private fun getOrNewInstance() =
        try { editorView.get()!! }
        catch (_: Throwable) {
            model.singleClass.newInstance().apply { (editorView as EditorView<Any>).set.invoke(this) }
        }


    override fun select(t: Field, event: InventoryClickEvent) {
        println(t.returnType)
        val type = try { t.returnType } catch (_: Throwable) { model }
        println(model)
        val mView = ModelViewImp(this, type)
        app.get<EditorRegistry>().getEditor(
            type, this, this,
//            FieldEditorImp<Any>(FieldViewImp(this, t))
            ModelEditorView(
                mView, EditorViewImp(mView,
                { getOrNewInstance().run { (t as KMutableProperty<*>).setter.call(this, it) } },
                { getOrNewInstance().run { t.getter.call(this) } }))
        )
            .open(event.whoClicked as Player)
    }

    override fun transform(t: Field) = createItem(Material.OAK_PLANKS, t.name, run {
        try {
            editorView.get()?.run { t.getter.call(this).toString() }?: ""
        } catch (_: Throwable) { "" }
    })

}