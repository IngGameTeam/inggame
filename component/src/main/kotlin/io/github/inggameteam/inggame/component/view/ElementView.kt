package io.github.inggameteam.inggame.component.view

import io.github.inggameteam.inggame.component.NameSpace

interface ElementView : Selector<Element>, ComponentServiceView {

    val nameSpace: NameSpace

}