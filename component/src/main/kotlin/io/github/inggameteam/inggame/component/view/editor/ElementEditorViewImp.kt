package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.model.ModelView
import kotlin.reflect.KClass

class ElementEditorViewImp<T : Any>(view: ModelView) : ElementEditorView<T>, ModelView by view