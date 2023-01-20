package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.singleClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.starProjectedType

@Suppress("UNCHECKED_CAST", "DEPRECATION")
interface ElementEditorView<T : Any> : EditorView<T>, ElementView {

    override val get: () -> T? get() = {
        try { componentService[nameSpace.name, element.first, Any::class] as T }
        catch(_: Throwable) {
            try {
                javaClass.genericSuperclass.singleClass.newInstance() as T
            } catch (_: Throwable) {
                null
            }
        }
    }
    override val set: (T) -> Unit get() = { componentService.set(nameSpace.name, element.first, it) }

}