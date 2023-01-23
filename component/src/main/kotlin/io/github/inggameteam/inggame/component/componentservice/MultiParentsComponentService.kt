package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import kotlin.reflect.KClass

@Suppress("NAME_SHADOWING")
class MultiParentsComponentService(
    override val name: String,
    private val rootComponent: () -> ComponentService?,
    private val components: Collection<ComponentService>,
    private val parentKey: Any?
) : ComponentService, AbstractNameSpaceComponentService() {

    init {
        if (components.isEmpty()) {
            throw AssertionError("an error occurred while create multi parents component service that empty components collection")
        }
    }

    override val parentComponent get() = components.first()

    override val layerPriority: Int by lazy { return@lazy components.maxOf { it.layerPriority } }

    private fun findParent(nameSpace: Any): ComponentService {
        return try { rootComponent()!![nameSpace, parentKey!!, Any::class]
            .let { name -> components.firstOrNull { it.name == name }!! } }
        catch (_: Throwable) { components.first() }
    }

    override fun <T : Any> get(nameSpace: Any, key: Any, clazz: KClass<T>): T {
        if (parentKey == key)
            throw AssertionError("an error occurred while perform get method parentKey and key is same")
        val nameSpace = uncoverDelegate(nameSpace)
        return findParent(nameSpace)[nameSpace, key, clazz]
    }

    override fun set(nameSpace: Any, key: Any, value: Any?) {
        findParent(nameSpace).set(nameSpace, key, value)
    }

    override fun getOrNull(name: Any): NameSpace? {
        return findParent(name).getOrNull(name)
    }

    override fun getAll(): Collection<NameSpace> {
        return emptyList()
    }

}