package io.github.inggameteam.inggame.party.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.party.wrapper.PartyRequest
import io.github.inggameteam.inggame.party.wrapper.PartyRequestImp

class PartyRequestInstanceRepo(componentService: ComponentService)
    : LayeredComponentService by componentService as LayeredComponentService
{
    fun removeIf(predicate: (PartyRequest) -> Boolean) {
        getAll(::PartyRequestImp).filter(predicate).forEach { removeNameSpace(it.nameSpace) }
    }
}