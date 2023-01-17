package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

abstract class AbstractNameSpaceComponentService : ComponentService {

    override val layerPriority: Int = run {
        var num = 0
        var parent: ComponentService = this
        try {
            do {
                parent = parent.parentComponent
                num++
            } while (true)
        } catch (_: Throwable) { }
         println(num)
        num
    }

    override fun get(name: Any): NameSpace {
        return getOrNull(name)?: throw NameSpaceNotFoundException(name)
    }

    override fun setParents(name: Any, value: Collection<Any>) {
        (getOrNull(name)?: newModel(name)).apply {
            parents = CopyOnWriteArraySet(value.map { uncoverDelegate(it) })
            parents = sortParentsByPriority(parents)
        }
    }

    override fun getParents(name: Any): CopyOnWriteArraySet<Any> {
        return get(name).parents
    }

    override fun addParents(name: Any, value: Any) {
        get(name).apply {
            parents.add(uncoverDelegate(value))
            parents = sortParentsByPriority(parents)
        }
    }

    override fun removeParents(name: Any, value: Any) {
        get(name).apply {
            parents.remove(uncoverDelegate(value))
            parents = sortParentsByPriority(parents)
        }
    }

    override fun hasParents(name: Any, value: Any): Boolean {
        return get(name).parents.contains(uncoverDelegate(value))
    }

    override fun newModel(name: Any): NameSpace {
        return NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap())
    }

}