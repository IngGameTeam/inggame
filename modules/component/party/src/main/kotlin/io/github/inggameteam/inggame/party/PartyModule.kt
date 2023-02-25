package io.github.inggameteam.inggame.party

import io.github.inggameteam.inggame.component.classOf
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.party.component.*
import io.github.inggameteam.inggame.party.handler.PartyHelper
import io.github.inggameteam.inggame.party.wrapper.PartyAlert
import io.github.inggameteam.inggame.party.wrapper.PartyPlayer
import io.github.inggameteam.inggame.party.wrapper.PartyRequest
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler

class PartyModule(plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onLoad(event: ComponentLoadEvent) {
        event.registerClass {
            classOf(PartyAlert::class)
            classOf(PartyPlayer::class)
            classOf(PartyRequest::class)
            classOf(::PartyHelper)
            classOf(::PartyInstanceService)
        }
        event.addModule(newModule("party-player", ::PartyPlayerService))
        event.addModule(newModule("party-instance", ::PartyInstanceRepo))
        event.addModule(newModule("party-request-instance", ::PartyRequestInstanceRepo))
        event.componentServiceDSL.apply {
            this
                .cs("party-player", type = ComponentServiceType.MASKED)
                .cs("party-instance", type = ComponentServiceType.LAYER)
                .cs("party-resource", type = ComponentServiceType.MULTI).csc {
                    cs("party-template-korean", type = ComponentServiceType.RESOURCE)
                }
                .cs("handler")
            this
                .cs("party-request-instance", type = ComponentServiceType.LAYER)
                .cs("party-resource")
        }
    }

}