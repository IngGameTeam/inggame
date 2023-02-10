package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.OpenView
import io.github.inggameteam.inggame.component.view.entity.View

interface Editor : View, OpenView {

    val previousSelector: Selector<*>?

    fun getEditorName(): String = javaClass.simpleName

    val editor get() = getSelector(getEditorName())


}