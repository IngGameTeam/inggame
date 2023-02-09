package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface LayeredComponentService : ComponentService, SaveComponentService {

    fun load(name: Any, new: Boolean = false)
    fun unload(name: Any, save: Boolean)
    fun save(name: Any)

}