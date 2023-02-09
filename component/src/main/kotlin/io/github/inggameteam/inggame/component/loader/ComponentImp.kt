package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

class ComponentImp(wrapper: Wrapper) : Component, SimpleWrapper(wrapper) {
    override val componentType: ComponentServiceDSL.ComponentServiceType by nonNull
    override val isSavable: Boolean by nonNull
}