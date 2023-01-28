package io.github.inggameteam.inggame.component.event

import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.MultiParentsComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentServiceImp
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class ComponentServiceRegisterEvent(
    private val root: ComponentServiceDSL = ComponentServiceDSL("root", ArrayList(), ArrayList()),
    private val instanceRegistry: ComponentServiceDSL = root.run { "player" cs "player-instance" cs "multi-player" csc { } },
    private val languageRegistry: ComponentServiceDSL = root.run { "language" key "language" csc { } },
    private val resourceRegistry: ComponentServiceDSL = root.run { "resource" csc { "singleton" cs "default" } },
) : Event() {

    private val modules: ArrayList<Module> = ArrayList()

    fun addModule(name: String, block: (ComponentService) -> Any) {
        modules.add(module { single(named(name)) { block(get(named(name))) } })
    }

    fun addModule(module: Module) {
        modules.add(module)
    }

    private fun register(name: Collection<String>, registry: ComponentServiceDSL, suffix: String) {
        val iterator = name.iterator()
        var last: ComponentServiceDSL = registry
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (iterator.hasNext()) {
                last = last.run { next cs iterator.next() }
            }
        }
        last.run { last cs suffix }
    }

    fun registerInstance(vararg name: String) {
        register(name.toList(), instanceRegistry, "language")
    }

    fun registerResource(vararg name: String) {
        register(name.toList(), resourceRegistry, "default")
    }

    fun registerLanguage(vararg name: String) {
        register(name.toList(), languageRegistry, "resource")
    }

    private fun getRegistry() = ArrayList(root.registry)

    fun getNewModule() = getRegistry().let { registry ->
        registry.map { cs ->
            module {
                single {
                    if (cs.parents.size == 1)
                        MultiParentsComponentService(
                            cs.name,
                            { get(named(registry.first().name)) },
                            cs.parents.map { get(named(it)) },
                            cs.key
                        )
                    else if (cs.isLayer) LayeredComponentServiceImp(
                        get(named(cs.name)),
                        get(),
                        get(named(cs.parents.first())),
                        cs.name
                    )
                    else ResourceComponentServiceImp(
                        get(named(cs.name)),
                        get(),
                        get(named(cs.parents.first())),
                        cs.name
                    )
                } bind ComponentService::class
            }
        }
    }.run {
        val result = ArrayList(this)
        result.addAll(modules)
        result
    }

    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}
