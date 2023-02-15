package io.github.inggameteam.inggame.component.view.entity

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.EmptyComponentServiceImp
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

class ModelViewImp(elementView: ElementView, override val model: Model)
    : ModelView, ElementView by elementView

fun createEmptyModelView(view: View, model: Model) = ModelViewImp(
    ElementViewImp(NameSpaceViewImp(
        ComponentServiceViewImp(view, EmptyComponentServiceImp("Unit")),
        NameSpace("Unit", CopyOnWriteArraySet(), ConcurrentHashMap())
    ), Pair(Unit, Unit)), model)