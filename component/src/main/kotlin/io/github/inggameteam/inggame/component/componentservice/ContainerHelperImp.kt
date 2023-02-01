package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
class ContainerHelperImp<CONTAINER : Wrapper, ELEMENT : Wrapper>(
    private val componentService: LayeredComponentService,
    private val keyComponent: LayeredComponentService,
    private val keyAssign: Any,
    private val keyList: Any,
) : ContainerHelper<CONTAINER, ELEMENT> {

    override fun has(container: CONTAINER): Boolean {
        val container = uncoverDelegate(container)
        return componentService.getOrNull(container) !== null
    }

    override fun create(container: CONTAINER, parent: Any): CONTAINER {
        val uncoveredContainer = uncoverDelegate(container)
        componentService.load(uncoveredContainer, true)
        val parent = uncoverDelegate(parent)
        componentService[uncoveredContainer].apply { parents.add(parent) }.name
        return container
    }

    override fun remove(container: CONTAINER) {
        val uncoveredContainer = uncoverDelegate(container)
        getList(container).forEach(::left)
        componentService.unload(uncoveredContainer, false)
    }

    override fun join(container: CONTAINER, key: ELEMENT) {
        val uncoveredContainer = uncoverDelegate(container)
        val uncoveredKey = uncoverDelegate(key)
        left(key)
        keyComponent.load(uncoveredKey, true)
        keyComponent.set(uncoveredKey, keyAssign, container)
        keyComponent.addParents(uncoveredKey, uncoveredContainer)
        getList(container).add(key)
    }

    override fun left(key: ELEMENT) {
        val container = try { keyComponent[key, keyAssign, Any::class] as CONTAINER } catch (_: Throwable) { return }
        getList(container).remove(key)
        keyComponent.unload(key, false)
    }

    private fun getList(container: CONTAINER): CopyOnWriteArraySet<ELEMENT> {
        return try { componentService[container, keyList, Any::class] as CopyOnWriteArraySet<ELEMENT> }
        catch (_: Throwable) { CopyOnWriteArraySet<ELEMENT>().apply { componentService.set(container, keyList, this) } }
    }

}