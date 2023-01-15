package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFoundException
import io.github.inggameteam.inggame.component.delegate.Delegate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

abstract class AbstractNameSpaceComponentService : ComponentService {

    override fun get(name: Any): NameSpace {
        return getOrNull(name)?: throw NameSpaceNotFoundException(name)
    }

    override fun setParents(name: Any, value: Collection<Any>) {
        (getOrNull(name)?: newModel(name)).apply {
            parents = CopyOnWriteArraySet(value.map { if (it is Delegate) it.nameSpace else it })
            parents = sortParentsByPriority(parents)
        }
    }

    override fun getParents(name: Any): CopyOnWriteArraySet<Any> {
        return get(name).parents
    }

    override fun addParents(name: Any, value: Any) {
        get(name).apply {
            parents.add(if (value is Delegate) value.nameSpace else value)
            parents = sortParentsByPriority(parents)
        }
    }

    override fun removeParents(name: Any, value: Any) {
        get(name).apply {
            parents.remove(if (value is Delegate) value.nameSpace else value)
            parents = sortParentsByPriority(parents)
        }
    }

    override fun hasParents(name: Any, value: Any): Boolean {
        return get(name).parents.contains(if (value is Delegate) value.nameSpace else value)
    }

    override fun newModel(name: Any): NameSpace {
        return NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap())
    }

}