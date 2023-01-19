package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.SubClassRegistry
import io.github.inggameteam.inggame.component.view.editor.*
import io.github.inggameteam.inggame.component.view.editor.EditorView
import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.editor.FieldEditorImp
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.selector.ModelFieldSelector
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.selector.SubTypeSelector
import io.github.inggameteam.inggame.mongodb.Model
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KFunction2
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.javaType

class EditorRegistry(private val subClassRegistry: SubClassRegistry) {

    fun getEditor(type: KType, elementView: ElementView, selector: Selector<*>?, editorView: EditorView<*> = ElementEditorViewImp<Any>(elementView)): Editor {
        val clazz = run {
            val javaType = type.javaType
            if (javaType is Class<out Any>) {
                javaType
            } else {
                val actualTypeArguments = (javaType as ParameterizedType).actualTypeArguments[0]
                println(actualTypeArguments)
                actualTypeArguments as Class<*>
            }
        }
            .let { clazz ->
                elementView.run {
                    try { componentService[nameSpace.name, element.first, clazz.kotlin].javaClass }
                    catch (_: Throwable) { clazz }
                }
            }
        println(clazz)
        if (clazz.isEnum) {
            return EnumFieldEditor(ModelViewImp(elementView, clazz.kotlin), editorView, selector)
        } else if (clazz.getAnnotation(Model::class.java) !== null) {

            val modelView = ModelViewImp(elementView, clazz.kotlin)
            try {
                subClassRegistry.getSubs(clazz.kotlin)
                return SubTypeSelector(modelView, selector)
            } catch (_: Throwable) { }
            return ModelFieldSelector(modelView, selector)
        }
        return this.map[clazz.kotlin.createType()]!!.invoke(editorView, selector)
    }

    val map: HashMap<KType, (EditorView<*>, Selector<*>?) -> Editor> = hashMapOf(
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

@Suppress("UNCHECKED_CAST")
private fun <T : Any> code(block: KFunction2<EditorView<T>, Selector<*>?, Editor>) = { editorView: EditorView<*>, selector: Selector<*>? ->
    block.invoke(editorView as EditorView<T>, selector)
}