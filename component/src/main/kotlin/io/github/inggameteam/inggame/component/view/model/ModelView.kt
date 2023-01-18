package io.github.inggameteam.inggame.component.view.model

import kotlin.reflect.KClass

typealias Model = KClass<*>
interface ModelView : ElementView {

    val model: Model

}