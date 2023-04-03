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
annotation class Resource(val value: String, val save: Boolean = true)

@Retention(AnnotationRetention.RUNTIME)
annotation class Custom(val value: String, val save: Boolean = true)

@Retention(AnnotationRetention.RUNTIME)
annotation class Layered(val value: String, val save: Boolean = true)

@Retention(AnnotationRetention.RUNTIME)
annotation class Masked(val value: String, val save: Boolean = true)

@Retention(AnnotationRetention.RUNTIME)
annotation class Multi(val value: String, val save: Boolean = true)

@Retention(AnnotationRetention.RUNTIME)
annotation class Singleton(val value: String, val save: Boolean = true)

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
                            } else {
                                fun Pair<String, Boolean>.module(
                                    type: ComponentServiceType,
                                    suffix: String,
                                    vararg parent: String
                                ): String = first.run {
                                    val name = this + suffix
                                    event.componentServiceRegistry.apply {
                                        (if (type === MULTI) {
                                            cs(name, type = type, root = "player-instance", key = name, isSavable = this@module.second)
                                        } else cs(name, type = type, isSavable = this@module.second))
                                            .apply {
                                                fun ComponentServiceDSL.appendLinked(parent: String): ComponentServiceDSL {
                                                    val parentName = this@run + parent
                                                    return registry.firstOrNull { it.name == parentName }
                                                        ?.also {
                                                            this@appendLinked.parents.remove("handler")
                                                            this@appendLinked.parents.remove("default")
                                                            this@appendLinked.parents.remove(parentName)
                                                            this@appendLinked.parents.add(parentName)
                                                        }
                                                        ?: this@appendLinked.cs(parentName, type = LINKED)
                                                            .apply {
                                                                this@appendLinked.parents.remove("handler")
                                                                cs("handler")
                                                            }
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


                                val resource = "-resource"
                                cls.java.getAnnotation(Resource::class.java)
                                    ?.run { value to save }?.module(RESOURCE, resource)
                                val multi = "-multi"
                                cls.java.getAnnotation(Multi::class.java)
                                    ?.run { value to save }?.module(MULTI, multi, resource)
                                val custom = "-custom"
                                cls.java.getAnnotation(Custom::class.java)
                                    ?.run { value to save }?.module(LAYER, custom, multi, resource)
                                val instance = "-instance"
                                cls.java.getAnnotation(Layered::class.java)
                                    ?.run { value to save }?.module(LAYER, instance, custom, multi, resource)
                                val player = "-player"
                                cls.java.getAnnotation(Masked::class.java)
                                    ?.run { value to save }?.module(MASKED, player, instance, custom, multi, resource)
                            }
                            null
                        }
                    } catch (_: Throwable) { null }
                }
                .toTypedArray())

        event.addModule(clazzModule.module)
    }

}