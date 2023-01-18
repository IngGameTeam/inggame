package io.github.inggameteam.inggame.component.view.model.editor

import io.github.inggameteam.inggame.component.view.model.View

interface EditorView<T : Any> : View {

    val set: (T) -> Unit
    val get: () -> T?

}