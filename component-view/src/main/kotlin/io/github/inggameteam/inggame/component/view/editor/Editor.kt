package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.OpenView
import io.github.inggameteam.inggame.component.view.model.View
import io.github.inggameteam.inggame.component.view.selector.Selector

interface Editor : View, OpenView {

    val previousSelector: Selector<*>?

    fun getEditorName(): String = javaClass.simpleName

    val editor get() = getSelector(getEditorName())


}