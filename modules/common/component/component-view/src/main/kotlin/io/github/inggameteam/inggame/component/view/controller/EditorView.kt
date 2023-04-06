package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.entity.View

interface EditorView<T : Any> : View {

    val set: (T) -> Unit
    val get: () -> T?

}