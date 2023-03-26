package io.github.inggameteam.inggame.component

import com.google.common.reflect.ClassPath
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent
import io.github.inggameteam.inggame.component.loader.ComponentServiceDSL
import io.github.inggameteam.inggame.component.loader.ComponentServiceType
import io.github.inggameteam.inggame.component.loader.ComponentServiceType.*
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.utils.Helper
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.fastForEach
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
                            if (cls.java.isInterface.not() && (cls.isSubclassOf(Handler::class) || cls.java.getAnnotation(Helper::class.java) !== null)) {
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
                                    vararg parent: String
                                ): String {
                                    val name = this@module + suffix
                                    event.componentServiceRegistry.apply {
                                        println("$name=$type")
                                        (if (type === MULTI) {
                                            cs(name, type = type, root = "player-instance", key = name)
                                        } else cs(name, type = type))
                                            .apply {
                                                fun ComponentServiceDSL.appendLinked(parent: String): ComponentServiceDSL {
                                                    println("${this.name}=${type.name} (link)")
                                                    val parentName = this@module + parent
                                                    return registry.firstOrNull { it.name == parentName }
                                                        ?.also {
                                                            this@appendLinked.parents.remove("handler")
                                                            this@appendLinked.parents.add(parentName)
                                                        }
                                                        ?: this@appendLinked.cs(parentName, type = LINKED)
                                                            .apply { cs("handler") }
                                                }

                                                var lastCS = this
                                                parent.fastForEach { lastCS = lastCS.appendLinked(it) }
                                            }
                                    }
                                    clazzModule.module.single {
                                        cls.primaryConstructor?.call(get<ComponentService>(named(name)))
                                    }.withOptions { this.secondaryTypes = listOf(cls) }
                                    return name
                                }


                                cls.java.getAnnotation(Resource::class.java)
                                    ?.value?.module(RESOURCE, "–resource")
                                cls.java.getAnnotation(Multi::class.java)
                                    ?.value?.module(MULTI, "–multi", "-resource")
                                cls.java.getAnnotation(Custom::class.java)
                                    ?.value?.module(LAYER, "–custom", "-multi", "-resource")
                                cls.java.getAnnotation(Layered::class.java)
                                    ?.value?.module(LAYER, "–instance", "-custom", "-multi", "-resource")
                                cls.java.getAnnotation(Masked::class.java)
                                    ?.value?.module(MASKED, "-player", "-instance", "-custom", "-multi", "-resource")
                            }
                            null
                        }
                    } catch (_: Throwable) { null }
                }
                .toTypedArray())
        println("-".repeat(10))
        println(event.componentServiceRegistry.registry.joinToString("\n"))
        println("-".repeat(10))

        event.addModule(clazzModule.module)
    }

}