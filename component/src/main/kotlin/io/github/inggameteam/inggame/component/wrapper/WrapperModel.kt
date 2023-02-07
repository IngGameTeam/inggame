package io.github.inggameteam.inggame.component.wrapper

import io.github.inggameteam.inggame.utils.Model
import io.github.inggameteam.inggame.utils.fastUUID
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.ext.getFullName

@Model
class WrapperModel(
    var componentService: String,
    var nameSpace: String,
    var wrapperClass: String
) {

    constructor(wrapper: Wrapper)
            : this(wrapper.component.name, wrapper.nameSpace.toString(), wrapper::class.getFullName())

    fun createWrapper(koin: Koin): Wrapper {
        return Class.forName(wrapperClass).getConstructor(Wrapper::class.java).newInstance(SimpleWrapper(
            nameSpace.run { try { fastUUID() } catch (_: Throwable) { this } },
            koin.get(named(componentService))
        )) as Wrapper
    }
}