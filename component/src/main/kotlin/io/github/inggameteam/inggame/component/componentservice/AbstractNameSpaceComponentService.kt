package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

abstract class AbstractNameSpaceComponentService : ComponentService {

    override fun get(name: Any): NameSpace {
        return getOrNull(name)?: throw AssertionError("$name is not exist")
    }
    override fun setParents(name: Any, value: Collection<Any>) {
        get(name).parents = CopyOnWriteArraySet(value)
    }

    override fun getParents(name: Any): CopyOnWriteArraySet<Any> {
        return get(name).parents
    }

    override fun addParents(name: Any, parents: Any) {
        get(name).parents.add(parents)
    }

    override fun removeParents(name: Any, parents: Any) {
        get(name).parents.remove(parents)
    }

    override fun hasParents(name: Any, parents: Any): Boolean {
        return get(name).parents.contains(parents)
    }

    override fun newModel(name: Any): NameSpace {
        return NameSpace(name, CopyOnWriteArraySet(), ConcurrentHashMap())
    }

}