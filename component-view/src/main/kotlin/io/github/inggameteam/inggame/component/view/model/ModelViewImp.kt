package io.github.inggameteam.inggame.component.view.model

class ModelViewImp(elementView: ElementView, override val model: Model)
    : ModelView, ElementView by elementView