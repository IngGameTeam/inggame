package io.github.inggameteam.inggame.component

import com.google.common.reflect.ClassPath
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.component.loader.ComponentServiceType.*
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import org.bukkit.event.EventHandler
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor

@Retention(AnnotationRetention.RUNTIME)
annotation class Resource(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Layered(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Masked(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Multi(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Singleton(val value: String)

class ComponentServiceBean(val plugin: IngGamePlugin) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onComponentLoad(event: ComponentLoadEvent) {
        val loader = plugin.javaClass.classLoader
        val clazzModule = ClassModule(module(createdAtStart = true) {})
        event.registerClass(
            *ClassPath.from(loader).topLevelClasses
                .apply { println(this) }
                .map { it.load().kotlin }
                .mapNotNull { cls ->
                    if (cls.isSubclassOf(Wrapper::class)) cls else {
                        if (cls.isSubclassOf(Handler::class) || cls.java.getAnnotation(Helper::class.java) !== null) {
                            clazzModule.module.single {
                                val constructor = cls.primaryConstructor ?: return@single cls.createInstance()
                                constructor.call(*constructor.parameters
                                    .map { it.type.toString() }
                                    .map { this.get<Any>(Class.forName(it).kotlin, null, null) }
                                    .toTypedArray())
                            }.withOptions { this.secondaryTypes = listOf(cls) }
                            null
                        } else {
                            val singleton = cls.java.getAnnotation(Singleton::class.java)?.value
                            if (singleton !== null) {
                                clazzModule.module.single {
                                    val componentService = get<ComponentService>(named("singleton"))
                                    componentService.addNameSpace(singleton)
                                    cls.primaryConstructor?.call(SimpleWrapper(singleton, componentService))
                                }
                            } else {
                                fun String.module(type: ComponentServiceType, suffix: String, parent: String? = null): String {
                                    val name = this@module + suffix
                                    event.componentServiceRegistry.apply {
                                        (parent?.run { cs(this) }?: this).apply {
                                            if (type === MULTI) {
                                                cs(name, type = type, root = "player-instance", key = name)
                                            } else cs(name, type=type)
                                        }
                                    }
                                    clazzModule.module.single {
                                        cls.primaryConstructor?.call(get<ComponentService>(named(name)))
                                    }
                                    return name
                                }

                                val multi = cls.java.getAnnotation(Multi::class.java)
                                    ?.value?.module(MULTI, "–resource", "handler")
                                val resource = cls.java.getAnnotation(Resource::class.java)
                                    ?.value?.module(RESOURCE , "–resource", "handler")
                                val instance = cls.java.getAnnotation(Layered::class.java)
                                    ?.value?.module(LAYER, "–instance", multi?: resource?: "handler")
                                cls.java.getAnnotation(Masked::class.java)
                                    ?.value?.module(MASKED, "-player", instance?: multi?: resource?: "handler")
                            }
                            null
                        }
                    }
                }
    .toTypedArray())
    event.addModule(clazzModule.module)
}

}