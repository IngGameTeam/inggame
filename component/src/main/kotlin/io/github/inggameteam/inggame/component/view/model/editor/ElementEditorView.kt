package io.github.inggameteam.inggame.component.view.model.editor

@Suppress("UNCHECKED_CAST")
interface ElementEditorView<T : Any> : EditorView<T>, ElementView {

    override val get: () -> T? get() = { componentService[nameSpace.name, element.first, Any::class] as T }
    override val set: (T) -> Unit get() = { componentService.set(nameSpace.name, element.first, it) }

}