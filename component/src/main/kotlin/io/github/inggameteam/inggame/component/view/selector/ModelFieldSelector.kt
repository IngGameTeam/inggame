package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.EditorRegistry
import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.model.FieldViewImp
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.singleClass
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
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

    @Suppress("DEPRECATION")
    private fun getOrNewInstance() =
        try { editorView.get()!! }
        catch (_: Throwable) { model.singleClass.newInstance()
            .apply { componentService.set(nameSpace.name, element.first, this) } }


    override fun select(t: Field, event: InventoryClickEvent) {
        app.get<EditorRegistry>().getEditor(t.returnType, this, this,
//            FieldEditorImp<Any>(FieldViewImp(this, t))
            ModelEditorView(modelView, EditorViewImp(modelView,
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