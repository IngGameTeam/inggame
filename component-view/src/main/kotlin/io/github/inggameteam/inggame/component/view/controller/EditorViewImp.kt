package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.entity.View

class EditorViewImp<T : Any>(view: View, override val set: (T) -> Unit, override val get: () -> T?)
    : EditorView<T>, View by view