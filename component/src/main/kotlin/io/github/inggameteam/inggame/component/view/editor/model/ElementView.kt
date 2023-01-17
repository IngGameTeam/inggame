package io.github.inggameteam.inggame.component.view.editor.model

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.view.editor.selectors.Selector

interface ElementView : Selector<Element>, ComponentServiceView {

    val nameSpace: NameSpace

}