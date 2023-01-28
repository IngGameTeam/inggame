package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.selector.Selector

class StringEditor(
    view: EditorView<String>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<String> by view, ChatEditor {

    override fun set(any: String) { set.invoke(any.replace("\\n", "\n")) }
    override fun get(): String? = get.invoke()

}