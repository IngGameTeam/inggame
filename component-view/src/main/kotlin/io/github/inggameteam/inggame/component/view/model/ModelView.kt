package io.github.inggameteam.inggame.component.view.model

import kotlin.reflect.KType

typealias Model = KType
interface ModelView : ElementView {

    val model: Model

}