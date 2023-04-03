package io.github.inggameteam.inggame.component.loader

import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.utils.SafeSetWithToString

interface Component : Wrapper {

    val componentType: ComponentServiceType
    val isSavable: Boolean
    val componentParentList: SafeSetWithToString<String>

}