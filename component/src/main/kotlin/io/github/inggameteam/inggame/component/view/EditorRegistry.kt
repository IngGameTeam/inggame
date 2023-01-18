package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.Subs
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.editor.EditorView
import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.selector.ModelFieldSelector
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.selector.SubTypeSelector
import io.github.inggameteam.inggame.mongodb.Model
import kotlin.reflect.KFunction2
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.javaType
import kotlin.reflect.jvm.javaType

class EditorRegistry(private val propertyRegistry: PropertyRegistry) {

    fun getEditor(type: KType, elementView: ElementView, selector: Selector<*>?): Editor {
        val clazz = type.javaType as Class<*>
        if (clazz.getAnnotation(Model::class.java) !== null) {
            val modelView = ModelViewImp(elementView, clazz.kotlin)
            clazz.getAnnotation(Subs::class.java)?.also {
                return SubTypeSelector(modelView, selector)
            }
            return ModelFieldSelector(modelView, selector)
        }
        return this.map[type]!!.invoke(elementView, selector)
    }

    val map: HashMap<KType, (ElementView, Selector<*>?) -> Editor> = hashMapOf(
        *listOf(
            Byte::class, Short::class, Int::class, Long::class,
            Float::class, Double::class,
            java.lang.Byte::class, java.lang.Short::class, java.lang.Integer::class, java.lang.Long::class,
            java.lang.Float::class, java.lang.Double::class, )
            .map { it.createType() to code(::NumberEditor) }.toTypedArray(),

        java.lang.String::class.createType() to code(::StringEditor),
        String::class.createType() to code(::StringEditor),
        Boolean::class.createType() to code(::BooleanEditor)
    )

}

private fun <T : Any> code(block: KFunction2<EditorView<T>, Selector<*>?, Editor>) = { elementView: ElementView, selector: Selector<*>? ->
    block.invoke(ElementEditorViewImp(elementView), selector)
}