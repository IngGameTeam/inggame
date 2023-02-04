package io.github.inggameteam.inggame.component.event

import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.componentservice.*
import io.github.inggameteam.inggame.component.helper.AddToSaveRegistry
import io.github.inggameteam.inggame.component.ClassModule
import io.github.inggameteam.inggame.utils.ClassRegistry
import io.github.inggameteam.inggame.utils.fastToString
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass


class ComponentServiceRegisterEvent(
    private val root: ComponentServiceDSL = ComponentServiceDSL("null", ArrayList(), ArrayList(), isMulti = true)
        .apply { "root" isMulti true csc {} },
) : Event() {


    private val modules: ArrayList<Module> = ArrayList()

    fun addModule(vararg module: Module) {
        modules.addAll(module)
    }

    fun registerClass(block: ClassModule.() -> Unit) {
        addModule(module(createdAtStart = true) {
            val classModule = ClassModule(this).apply(block)
            factory(named(randomUUID().fastToString())) {
                ClassRegistry(*classModule.classes.toTypedArray())
            }
        })
    }

    fun registerClass(vararg classes: KClass<*>) {
        addModule(module {
            factory(named(randomUUID().fastToString())) {
                ClassRegistry(*classes)
            }
        })
    }

    fun register(block: ComponentServiceDSL.() -> Unit) {
        root.apply {
            "root" csc(block)
        }
    }

    private fun getRegistry() = ArrayList(root.registry)

    fun getNewModule() = getRegistry().let { registry ->
        registry.map { cs ->
            println(cs)
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
                        .apply { if (cs.isSavable) AddToSaveRegistry(this, get()) }
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

inline fun <reified T : Any> newModule(name: String, crossinline block: (ComponentService) -> T) = module(createdAtStart = true) {
    single { block(get(named(name))) } bind T::class
}
