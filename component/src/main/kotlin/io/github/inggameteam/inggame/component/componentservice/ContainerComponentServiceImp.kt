package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
class ContainerComponentServiceImp(
    private val componentService: LayeredComponentService,
    private val keyComponent: LayeredComponentService,
    private val keyAssign: Any,
    private val keyList: Any,
) : ContainerComponentService {

    override fun has(container: Any): Boolean {
        val container = uncoverDelegate(container)
        return componentService.getOrNull(container) !== null
    }

    override fun create(container: Any, name: Any): Any {
        val container = uncoverDelegate(container)
        val name = uncoverDelegate(name)
        componentService.load(container, true)
        return componentService[container].apply { parents.add(name) }.name
    }

    override fun remove(container: Any) {
        val container = uncoverDelegate(container)
        getList(container).forEach(::left)
        componentService.unload(container, false)
    }

    override fun join(container: Any, key: Any) {
        val container = uncoverDelegate(container)
        val key = uncoverDelegate(key)
        left(key)
        keyComponent.load(key, true)
        keyComponent.set(key, keyAssign, container)
        keyComponent.addParents(key, container)
        getList(container).add(key)
    }

    override fun left(key: Any) {
        val key = uncoverDelegate(key)
        val container = try { keyComponent[key, keyAssign, Any::class] } catch (_: Throwable) { return }
        getList(container).remove(key)
        keyComponent.unload(key, false)
    }

    private fun getList(container: Any): CopyOnWriteArraySet<Any> {
        return try { componentService[container, keyList, Any::class] as CopyOnWriteArraySet<Any> }
        catch (_: Throwable) { CopyOnWriteArraySet<Any>().apply { componentService.set(container, keyList, this) } }
    }

}