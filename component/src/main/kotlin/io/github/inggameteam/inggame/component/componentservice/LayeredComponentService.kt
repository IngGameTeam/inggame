package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace

interface LayeredComponentService : ComponentService, SaveComponentService {

    fun load(name: Any)
    fun unload(name: Any, save: Boolean)
    fun save(name: Any)
    fun getAll(): ArrayList<NameSpace>


}