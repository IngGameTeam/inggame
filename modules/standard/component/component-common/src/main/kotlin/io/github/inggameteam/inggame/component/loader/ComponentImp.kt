package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

class ComponentImp(wrapper: Wrapper) : Component, SimpleWrapper(wrapper) {
    override val componentType: ComponentServiceType by nonNull
    override val isSavable: Boolean by nonNull
    override val parentsList: ArrayList<String> by nonNull
}