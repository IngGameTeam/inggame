package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.component.view.editor.EditorView
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties

@Suppress("UNCHECKED_CAST")
interface ModelEditorView<T : Any> : EditorView<T>, ModelView {


    @Suppress("DEPRECATION")
    private fun getOrNewInstance() =
        try { componentService[nameSpace.name, element.first, model] as T }
        catch (_: Throwable) { model.java.newInstance()
            .apply { componentService.set(nameSpace.name, element.first, this) } as T }

    override val get: () -> T?
        get() = { getOrNewInstance()
            .run { this.javaClass.kotlin.declaredMemberProperties.first { it.name == element.first }.get(this) as T } }

    override val set: (T) -> Unit
        get() = { getOrNewInstance()
            .run { this.javaClass.kotlin.declaredMemberProperties.first { it.name == element.first } as KMutableProperty<*> }
            .run { this.setter.call(it) } }

}