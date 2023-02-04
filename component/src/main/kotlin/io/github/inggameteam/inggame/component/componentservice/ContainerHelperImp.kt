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
        componentService.addParents(uncoveredContainer, parent)
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
        keyComponent.set(uncoveredKey, keyAssign, uncoveredContainer)
        println(container.nameSpace)
        key.addParents(container)
        getList(container).add(key)
    }

    override fun left(key: ELEMENT) {
        val container = try { keyComponent.find(key, keyAssign) as CONTAINER } catch (_: Throwable) { return }
        getList(container).remove(key)
        keyComponent.unload(key, false)
    }

    private fun getList(container: CONTAINER): CopyOnWriteArraySet<ELEMENT> {
        return try { componentService.find(container, keyList) as CopyOnWriteArraySet<ELEMENT> }
        catch (_: Throwable) { CopyOnWriteArraySet<ELEMENT>().apply { componentService.set(container, keyList, this) } }
    }

}