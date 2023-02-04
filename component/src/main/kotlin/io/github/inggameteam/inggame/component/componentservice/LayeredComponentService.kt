package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.NameSpaceNotFound
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import io.github.inggameteam.inggame.utils.fastFirstOrNull
import io.github.inggameteam.inggame.utils.fastForEach

interface LayeredComponentService : ComponentService, SaveComponentService {

    fun load(name: Any, new: Boolean = false)
    fun unload(name: Any, save: Boolean)
    fun save(name: Any)

    fun <T : Any> getAll(block: (Wrapper) -> T): List<T> {
        return this.getAll().map(NameSpace::name).map { get(it, block) }
    }

}