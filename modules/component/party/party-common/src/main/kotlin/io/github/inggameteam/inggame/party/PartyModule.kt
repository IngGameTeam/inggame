package io.github.inggameteam.inggame.party

import com.google.common.reflect.ClassPath
import io.github.inggameteam.inggame.component.*
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.event.newModule
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.party.component.*
import io.github.inggameteam.inggame.party.handler.*
import io.github.inggameteam.inggame.party.wrapper.*
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.core.module.dsl.new
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ext.getFullName
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor

class PartyModule(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onLoad(event: ComponentLoadEvent) {
        val loader = javaClass.classLoader
        val clazzModule = ClassModule(module(createdAtStart = true) {})
        event.registerClass(
            *ClassPath.from(loader).topLevelClasses
                .filter { cls -> cls.name.startsWith("io.github.inggameteam.inggame.party") }
                .apply { println(this) }
                .map { it.load().kotlin }
                .mapNotNull { cls ->
                    if (cls.isSubclassOf(Wrapper::class)) cls else {
                        clazzModule.module.single(named(cls.getFullName())) {
                            println(cls.primaryConstructor?.typeParameters?.first()?.name)
                        }
                        null
                    }
                }.toTypedArray())
        event.registerClass {

//            classOf(PartyAlert::class)
//            classOf(PartyPlayer::class)
//            classOf(PartyRequest::class)
//            classOf(PartyServer::class)
//            classOf(Party::class)
            classOf(::PartyHelper)
            classOf(::PartyInstanceService)
            classOf(::PartyRequestHelper)
//            classOf(::PartyPlayerLoader)
//            classOf(::DefaultPartyLoader)
//            classOf(::AnnouncePartyCreated)
//            classOf(::PartyCreationHandler)
        }
        event.addModule(createSingleton<PartyServer>(::PartyServerImp, "server", "singleton"))
        event.addModule(newModule("party-player", ::PartyPlayerService))
        event.addModule(newModule("party-instance", ::PartyInstanceRepo))
        event.addModule(newModule("party-request-instance", ::PartyRequestInstanceRepo))
        event.componentServiceRegistry.apply {
            this
                .cs("party-player", type = ComponentServiceType.MASKED)
                .cs("party-instance", type = ComponentServiceType.LAYER)
                .cs("party-resource", type = ComponentServiceType.MULTI, root = "player-instance", key = "party-language").csc {
                    cs("party-template-korean", isSavable = true, type = ComponentServiceType.RESOURCE)
                        .cs("handler")
                }
            this
                .cs("party-request-instance", type = ComponentServiceType.LAYER)
                .cs("party-resource")
        }
    }

}