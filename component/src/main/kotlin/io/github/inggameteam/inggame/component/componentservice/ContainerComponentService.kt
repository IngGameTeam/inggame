package io.github.inggameteam.inggame.component.componentservice

import io.github.inggameteam.inggame.component.NameSpace

interface ContainerComponentService {

    fun has(container: Any): Boolean

    fun create(container: Any, name: Any): Any

    fun remove(container: Any)

    fun join(container: Any, key: Any)

    fun left(key: Any)

}