package io.github.inggameteam.inggame.component.event

import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.componentservice.*
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class ComponentServiceRegisterEvent(
    private val root: ComponentServiceDSL = ComponentServiceDSL("root", ArrayList(), ArrayList(), isMulti = true),
) : Event() {


    private val modules: ArrayList<Module> = ArrayList()

    fun addModule(module: Module) {
        modules.add(module)
    }

    private fun register(name: Collection<String>, registry: ComponentServiceDSL, suffix: String) {
        val iterator = name.iterator()
        var last: ComponentServiceDSL = registry
        if (name.size == 1) {
            registry.run { name.first() cs suffix }
        }
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (iterator.hasNext()) {
                last = last.run { next cs iterator.next() }
            }
        }
        last.run { last cs suffix }
    }

    fun register(block: ComponentServiceDSL.() -> Unit) {
        block(root)
    }

    fun layer(name: String) {
        root.findComponentServiceDSL(name).isLayer = true
    }

    fun registerRoot(vararg name: String) {
        register(name.toList(), root, "default")
    }

    private fun getRegistry() = ArrayList(root.registry)

    fun getNewModule() = getRegistry().let { registry ->
        registry.map { cs ->
            module {
                single(named(cs.name)) {
                    if (cs.parents.isEmpty()) EmptyComponentServiceImp(cs.name)
                    else if (cs.isMulti || cs.key !== null && !cs.isLayer) {
                        val root by lazy { get<ComponentService>(named(cs.root ?: registry.first().name)) }
                        MultiParentsComponentService(
                            cs.name,
                            { root },
                            cs.parents.map { get(named(it)) },
                            cs.key
                        )
                    } else if (cs.isLayer) LayeredComponentServiceImp(
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
    }.run { arrayListOf(*this.toTypedArray(), *modules.toTypedArray()) }

    override fun getHandlers(): HandlerList { return HANDLERS }
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList { return HANDLERS }
    }
}

inline fun <reified T : Any> newModule(name: String, crossinline block: (ComponentService) -> T) = module {
    single { block(get(named(name))) } bind T::class
}
