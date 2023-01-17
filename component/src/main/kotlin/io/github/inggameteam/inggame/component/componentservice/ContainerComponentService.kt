package io.github.inggameteam.inggame.component.componentservice

interface ContainerComponentService {

    fun has(container: Any): Boolean

    fun create(container: Any, name: Any): Any

    fun remove(container: Any)

    fun join(container: Any, key: Any)

    fun left(key: Any)

}