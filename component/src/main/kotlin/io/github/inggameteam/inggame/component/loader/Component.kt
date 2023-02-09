package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface Component : Wrapper {

    val componentType: ComponentServiceDSL.ComponentServiceType
    val isSavable: Boolean

}