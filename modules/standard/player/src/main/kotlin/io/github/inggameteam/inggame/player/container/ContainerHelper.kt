package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface ContainerHelper<CONTAINER : Wrapper, ELEMENT : Wrapper> {

    fun has(container: CONTAINER): Boolean

    fun create(container: CONTAINER, parent: Any): CONTAINER

    fun remove(container: CONTAINER)

    fun join(container: CONTAINER, key: ELEMENT)

    fun left(key: ELEMENT)

}