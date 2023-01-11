package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace

interface ResourceComponentService : ComponentService {

    fun poolNameSpace()

    fun saveNameSpace()

    fun getNameSpaces(): ArrayList<NameSpace>

}