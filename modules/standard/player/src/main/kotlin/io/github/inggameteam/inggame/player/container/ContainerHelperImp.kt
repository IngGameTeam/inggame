package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.component.wrapper.uncoverDelegate
import io.github.inggameteam.inggame.utils.singleClass
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

@Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
class ContainerHelperImp<CONTAINER : Wrapper, ELEMENT : Wrapper>(
    private val componentService: LayeredComponentService,
    private val keyComponent: LayeredComponentService,
    private val keyAssign: KProperty<*> = ContainerElement<CONTAINER>::joined,
    private val keyList: KProperty<*> = Container<ELEMENT>::joinedPlayers,
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
        (measureTimeMillis{
            val uncoveredContainer = uncoverDelegate(container)
            if (container.component.getOrNull(uncoveredContainer) === null) return
            getList(container).forEach(::left)
            componentService.unload(uncoveredContainer, false)
        })
    }

    override fun join(container: CONTAINER, key: ELEMENT) {
        val uncoveredContainer = uncoverDelegate(container)
        val uncoveredKey = uncoverDelegate(key)
        left(key)
        keyComponent.unload(uncoveredKey, false)
        keyComponent.load(uncoveredKey, true)
        keyComponent.set(uncoveredKey, keyAssign.name, container)
        keyComponent.addParents(uncoveredKey, uncoveredContainer)
        getList(container).add(key)
    }

    override fun left(key: ELEMENT) {
        val uncoveredKey = uncoverDelegate(key)
        val container = try { keyComponent.find(uncoveredKey, keyAssign.name) as CONTAINER } catch (_: Throwable) { return }
        getList(container).remove(key)
        keyComponent.unload(uncoveredKey, false)
    }

    @Suppress("DEPRECATION")
    private fun getList(container: CONTAINER): MutableCollection<ELEMENT> {
        val get = try { container.getValue(keyList.name) } catch (_: Throwable) { null }
        return if (get is MutableCollection<*>) get as MutableCollection<ELEMENT>
        else {
            val col = keyList.returnType.singleClass.newInstance() as MutableCollection<ELEMENT>
            col.apply { container.set(keyList.name, this) }
        }
    }

}
