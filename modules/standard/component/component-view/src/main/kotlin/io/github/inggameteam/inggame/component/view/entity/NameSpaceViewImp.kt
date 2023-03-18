package io.github.inggameteam.inggame.component.view.entity

import io.github.inggameteam.inggame.component.NameSpace

class NameSpaceViewImp(
    componentServiceView: ComponentServiceView,
    override val nameSpace: NameSpace
) : NameSpaceView, ComponentServiceView by componentServiceView

