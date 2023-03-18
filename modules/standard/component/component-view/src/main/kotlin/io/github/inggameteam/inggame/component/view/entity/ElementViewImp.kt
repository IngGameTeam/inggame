package io.github.inggameteam.inggame.component.view.entity

import io.github.inggameteam.inggame.component.view.controller.Element

class ElementViewImp(nameSpaceView: NameSpaceView, override val element: Element)
    : ElementView, NameSpaceView by nameSpaceView