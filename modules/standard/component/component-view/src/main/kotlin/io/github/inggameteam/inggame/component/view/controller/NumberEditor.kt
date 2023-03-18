package io.github.inggameteam.inggame.component.view.controller

class NumberEditor(
    view: EditorView<Number>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<Number> by view, ChatEditor {

    override fun set(any: String) { set(any
        .run { toIntOrNull()?: toLongOrNull()?: toDoubleOrNull()
        ?: throw NumberFormatException("$this is not a number")})
    }
    override fun get(): String? = get.run { invoke()?.toString() }

}