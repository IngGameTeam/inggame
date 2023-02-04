package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpaceNotFound
import io.github.inggameteam.inggame.component.delegate.uncoverDelegate
import io.github.inggameteam.inggame.mongodb.MongoCodec
import io.github.inggameteam.inggame.mongodb.MongoRepo
import io.github.inggameteam.inggame.utils.fastFirstOrNull
import io.github.inggameteam.inggame.utils.fastForEach

class LayerMaskComponentService(
    repo: MongoRepo,
    codec: MongoCodec,
    parentComponent: ComponentService,
    name: String
) : LayeredComponentServiceImp(repo, codec, parentComponent, name) {

    override fun find(nameSpace: Any, key: Any): Any {
        val nameSpace = uncoverDelegate(nameSpace)
        val ns = getAll().fastFirstOrNull { it.name == nameSpace }
            ?: run { throw NameSpaceNotFound }
        return ns.elements.getOrDefault(key, null)
            ?: run {
                ns.parents.toArray().fastForEach { try { return parentComponent.find(it, key)
                } catch (_: Throwable) { } }
                throw NameSpaceNotFound
            }
    }

}