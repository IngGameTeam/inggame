package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.editor.ElementView
import io.github.inggameteam.inggame.component.view.model.editor.ElementEditorView

class ElementEditorViewImp<T : Any>(view: ElementView) : ElementEditorView<T>, ElementView by view