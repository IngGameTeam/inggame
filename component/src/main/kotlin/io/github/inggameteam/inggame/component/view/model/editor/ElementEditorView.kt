package io.github.inggameteam.inggame.component.view.model.editor

@Suppress("UNCHECKED_CAST")
interface ElementEditorView<T : Any> : EditorView<T>, ElementView {

    override val get: () -> T? get() = { componentService[nameSpace, element.first, Any::class] as T }
    override val set: (T) -> Unit get() = { componentService.set(nameSpace, element.first, it)}

}