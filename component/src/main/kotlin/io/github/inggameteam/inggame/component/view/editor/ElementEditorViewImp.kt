package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.ElementView
import kotlin.reflect.KClass

class ElementEditorViewImp<T : Any>(view: ElementView, kClass: KClass<T>) : ElementEditorView<T>, ElementView by view