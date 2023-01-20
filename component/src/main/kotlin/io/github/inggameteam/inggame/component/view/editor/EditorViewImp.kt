package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.View

class EditorViewImp<T : Any>(view: View, override val set: (T) -> Unit, override val get: () -> T?)
    : EditorView<T>, View by view