package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.component.view.selector.Element

class ElementViewImp(nameSpaceView: NameSpaceView, override val element: Element)
    : ElementView, NameSpaceView by nameSpaceView