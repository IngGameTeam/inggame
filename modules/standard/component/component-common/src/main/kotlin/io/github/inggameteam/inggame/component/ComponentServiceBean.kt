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
import kotlin.reflect.full.superclasses

@Retention(AnnotationRetention.RUNTIME)
annotation class Resource(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Custom(val value: String)

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
                .mapNotNull { try { it.load().takeIf { cls -> cls.classLoader == loader }?.kotlin } catch (_: Throwable) { null } }
                .mapNotNull { cls ->
                    try {
                        if (cls.isSubclassOf(Wrapper::class)) {
                            val singleton = cls.java.getAnnotation(Singleton::class.java)?.value
                            if (singleton !== null) {
                                clazzModule.module.single {
                                    val componentService = get<ComponentService>(named("singleton"))
                                    componentService.addNameSpace(singleton)
                                    cls.primaryConstructor?.call(SimpleWrapper(singleton, componentService))
                                }.withOptions { this.secondaryTypes = listOf(cls,
                                    *cls.superclasses.filter { it.isSubclassOf(Wrapper::class) }.toTypedArray()) }
                            }
                            cls
                        } else {
                            if (cls.java.isInterface && (cls.isSubclassOf(Handler::class) || cls.java.getAnnotation(Helper::class.java) !== null)) {
                                clazzModule.module.single {
                                    val constructor = cls.primaryConstructor ?: return@single cls.createInstance()
                                    constructor.call(*constructor.parameters
                                        .map { it.type.toString() }
                                        .map { this.get<Any>(Class.forName(it).kotlin, null, null) }
                                        .toTypedArray())
                                }.withOptions { this.secondaryTypes = listOf(cls) }
                                null
                            } else {
                                    fun String.module(
                                        type: ComponentServiceType,
                                        suffix: String,
                                        parent: String? = null
                                    ): String {
                                        val name = this@module + suffix
                                        event.componentServiceRegistry.apply {
                                                (if (type === MULTI) {
                                                    cs(name, type = type, root = "player-instance", key = name)
                                                } else cs(name, type = type))
                                                    .apply {
                                                        if (parent !== null) {
                                                            cs(this@module + parent)
                                                        } else {
                                                            cs("handler")
                                                        }
                                                    }
                                        }
                                        clazzModule.module.single {
                                            cls.primaryConstructor?.call(get<ComponentService>(named(name)))
                                        }.withOptions { this.secondaryTypes = listOf(cls) }
                                        return name
                                    }

                                    cls.java.getAnnotation(Multi::class.java)
                                        ?.value?.module(MULTI, "–resource")
                                    cls.java.getAnnotation(Resource::class.java)
                                        ?.value?.module(RESOURCE, "–resource")
                                    cls.java.getAnnotation(Custom::class.java)
                                        ?.value?.module(LAYER, "–custom", "-resource")
                                    cls.java.getAnnotation(Layered::class.java)
                                        ?.value?.module(LAYER, "–instance", "-custom")
                                    cls.java.getAnnotation(Masked::class.java)
                                        ?.value?.module(MASKED, "-player", "-instance")
                                }
                                null
                        }
                    } catch (_: Throwable) { null }
                }
    .toTypedArray())
    event.addModule(clazzModule.module)
}

}