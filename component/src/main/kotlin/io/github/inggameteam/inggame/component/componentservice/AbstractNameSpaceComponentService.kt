package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

abstract class AbstractNameSpaceComponentService : ComponentService {

    override val layerPriority: Int by lazy {
        var num = 0
        var parent: ComponentService = this
        do {
            parent = parent.parentComponent
            num++
        } while (parent !is EmptyComponentService)
        num
    }

    override fun get(name: Any): NameSpace {
        return getOrNull(name)?: throw NameSpaceNotFoundException(name)
    }

    override fun setParents(name: Any, value: Collection<Any>) {
        (getOrNull(name)?: newModel(name)).apply {
            parents = sortParentsByPriority(CopyOnWriteArraySet(value.map { uncoverDelegate(it) }))
        }
    }

    override fun getParents(name: Any): CopyOnWriteArraySet<Any> {
        return get(name).parents
    }

    override fun addParents(name: Any, value: Any) {
        get(name).apply {
            val parents = CopyOnWriteArraySet(parents)
            parents.add(uncoverDelegate(value))
            this.parents = sortParentsByPriority(parents)
        }
    }

    override fun removeParents(name: Any, value: Any) {
        get(name).apply {
            val parents = CopyOnWriteArraySet(parents)
            parents.remove(uncoverDelegate(value))
            this.parents = sortParentsByPriority(parents)
        }
    }

    override fun newModel(name: Any): NameSpace {
        return NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap())
    }

}