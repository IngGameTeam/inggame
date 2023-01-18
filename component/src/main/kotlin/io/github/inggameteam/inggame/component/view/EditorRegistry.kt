package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.PropertyRegistry
import io.github.inggameteam.inggame.component.view.editor.BooleanEditor
import io.github.inggameteam.inggame.component.view.editor.Editor
import io.github.inggameteam.inggame.component.view.editor.ElementEditorViewImp
import io.github.inggameteam.inggame.component.view.model.editor.EditorView
import io.github.inggameteam.inggame.component.view.editor.StringEditor
import io.github.inggameteam.inggame.component.view.model.editor.ElementView
import io.github.inggameteam.inggame.component.view.selector.Selector
import kotlin.reflect.KFunction2
import kotlin.reflect.KType
import kotlin.reflect.full.createType

class EditorRegistry(private val propertyRegistry: PropertyRegistry) {

    fun getEditor(name: String, elementView: ElementView, selector: Selector<*>): Editor {
        val prop = propertyRegistry.getProp(name)
        return this.map[prop.type]!!.invoke(elementView, selector)
    }

    val map: HashMap<KType, (ElementView, Selector<*>?) -> Editor> = hashMapOf(
        *listOf(
            Byte::class, Short::class, Int::class, Long::class,
            Float::class, Double::class, Char::class, String::class)
            .map { it.createType() to code(::StringEditor) }.toTypedArray(),
        Boolean::class.createType() to code(::BooleanEditor)
    )

}

private fun <T : Any> code(block: KFunction2<EditorView<T>, Selector<*>?, Editor>) = { elementView: ElementView, selector: Selector<*>? ->
    block.invoke(ElementEditorViewImp(elementView), selector)
}