package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.utils.ContainerState
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import java.util.concurrent.CopyOnWriteArraySet

interface Container<ELEMENT : Wrapper> : Wrapper {

    val containerName: String
    var containerState: ContainerState
    val containerJoined: SafeSetWithToString<ELEMENT>
    val playerLimitAmount: Int
    val startPlayersAmount: Int
}

open class ContainerImp<ELEMENT : ContainerElement<*>>(wrapper: Wrapper) : Container<ELEMENT>, SimpleWrapper(wrapper) {
    override val containerName: String by nonNull
    override var containerState: ContainerState by default { ContainerState.WAIT }
    override val containerJoined: SafeSetWithToString<ELEMENT> by default { SafeSetWithToString<ELEMENT>() }
    override val playerLimitAmount: Int by nonNull
    override val startPlayersAmount: Int by nonNull
}

