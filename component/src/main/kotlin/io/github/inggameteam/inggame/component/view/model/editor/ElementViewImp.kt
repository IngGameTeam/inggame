package io.github.inggameteam.inggame.component.view.model.editor

import io.github.inggameteam.inggame.component.view.model.NameSpaceView
import io.github.inggameteam.inggame.component.view.selector.Element
import javax.swing.text.View

class ElementViewImp(nameSpaceView: NameSpaceView, override val element: Element)
    : ElementView, NameSpaceView by nameSpaceView