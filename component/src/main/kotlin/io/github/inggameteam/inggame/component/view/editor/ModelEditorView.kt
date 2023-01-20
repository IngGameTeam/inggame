package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.model.ModelView
import io.github.inggameteam.inggame.component.view.model.View
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.koin.core.Koin

class ModelEditorView<T : Any>(val modelView: ModelView, editorView: EditorView<T>)
    : ModelView by modelView, EditorView<T> by editorView, View by modelView {
    override val app: Koin get() = modelView.app
    override val plugin: IngGamePlugin get() = modelView.plugin
    override val view: ComponentService get() = modelView.view
}