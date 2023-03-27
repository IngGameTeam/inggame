package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface Component : Wrapper {

    val componentType: ComponentServiceType
    val isSavable: Boolean
    val parents: ArrayList<String>

}