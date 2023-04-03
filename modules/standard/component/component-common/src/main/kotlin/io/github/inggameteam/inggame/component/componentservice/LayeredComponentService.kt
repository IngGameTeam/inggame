package io.github.inggameteam.inggame.component.componentservice

interface LayeredComponentService : ComponentService, SaveComponentService {

    fun load(name: Any, new: Boolean = false)
    fun unload(name: Any, save: Boolean)
    fun save(name: Any)

}