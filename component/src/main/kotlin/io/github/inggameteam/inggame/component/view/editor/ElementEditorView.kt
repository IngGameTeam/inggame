package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.ElementView

@Suppress("UNCHECKED_CAST")
interface ElementEditorView<T : Any> : EditorView<T>, ElementView {

    override val get: () -> T? get() = {
        try { componentService[nameSpace.name, element.first, Any::class] as T } catch(_: Throwable) { null }
    }
    override val set: (T) -> Unit get() = { componentService.set(nameSpace.name, element.first, it) }

}