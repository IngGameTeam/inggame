package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace

interface ResourceComponentService : ComponentService, SaveComponentService {

    fun poolNameSpace()

}