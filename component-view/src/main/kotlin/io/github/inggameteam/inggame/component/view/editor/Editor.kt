package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.component.view.OpenView
import io.github.inggameteam.inggame.component.view.model.View
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.wrapper.SelectorImp
import org.koin.core.qualifier.named

interface Editor : View, OpenView {

    val previousSelector: Selector<*>?

    fun getEditorName(): String = javaClass.simpleName

    val editor get() = getSelector(getEditorName())


}