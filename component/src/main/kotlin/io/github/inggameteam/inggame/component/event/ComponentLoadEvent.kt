package io.github.inggameteam.inggame.component.event

import io.github.inggameteam.inggame.component.ClassModule
import io.github.inggameteam.inggame.component.ComponentServiceDSL
import io.github.inggameteam.inggame.component.componentservice.*
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


class ComponentLoadEvent(val componentServiceDSL: ComponentServiceDSL) : Event() {

    val modules: ArrayList<Module> = ArrayList()

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
