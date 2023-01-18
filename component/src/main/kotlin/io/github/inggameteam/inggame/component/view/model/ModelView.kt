package io.github.inggameteam.inggame.component.view.model

import kotlin.reflect.KClass

interface ModelView : ElementView {

    val model: KClass<*>

}