package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.ModelView

@Suppress("UNCHECKED_CAST")
interface ElementEditorView<T : Any> : EditorView<T>, ModelView {

    override val get: () -> T? get() = {
        try { nameSpace.elements[element.first] as T }
        catch(_: Throwable) {
            try {
                try {
//                    ((model.javaType as ParameterizedType).actualTypeArguments[0] as Class<*>).newInstance()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                null
            } catch (_: Throwable) {
                null
            }
        }
    }
    override val set: (T) -> Unit get() = { componentService.set(nameSpace.name, element.first, it) }

}