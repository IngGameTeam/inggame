package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.ContainerState
import io.github.inggameteam.inggame.utils.JoinType
import io.github.inggameteam.inggame.utils.LeftType

abstract class ContainerHelperBase<CONTAINER : Container<ELEMENT>, ELEMENT : ContainerElement<CONTAINER>>(
    private val containerComponent: ComponentService,
    private val containerHelper: ContainerHelper<CONTAINER, ELEMENT>,
    private val hub: () -> CONTAINER,
) {

    open fun createContainer(parent: String, container: CONTAINER): CONTAINER {
        return containerHelper.create(container, parent)
    }

    open fun removeContainer(container: CONTAINER) {
        containerHelper.remove(container)
    }

    private fun requestJoin(requestedContainer: CONTAINER, element: ELEMENT, joinType: JoinType, sendMessage: Boolean = true): Boolean {
        if (requestedContainer == hub()) return true
        val containerAlert = element[::ContainerAlertImp]

        if (requestedContainer.containerJoined.contains(element)) {
            if (sendMessage) containerAlert.GAME_ALREADY_JOINED.send(element, requestedContainer.containerName)
        } else if (requestedContainer.containerState !== ContainerState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) containerAlert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(element, requestedContainer.containerName)
        } else if (requestedContainer.playerLimitAmount > 0
            && requestedContainer.containerJoined.filter { it.isPlaying }.size >= requestedContainer.playerLimitAmount
            && joinType === JoinType.PLAY
        ) {
            if (sendMessage) containerAlert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(element, requestedContainer.containerName)
        } else {
            return true
        }
        return false
    }

    open fun join(container: CONTAINER, element: ELEMENT, joinType: JoinType) = Unit

    fun joinContainer(container: CONTAINER, element: ELEMENT, joinType: JoinType = JoinType.PLAY): Boolean {
        leftGame(element, LeftType.DUE_TO_MOVE_ANOTHER)
        val containerAlert = element[::ContainerAlertImp]
        if (requestJoin(container, element, joinType, true)) {
            containerHelper.join(container, element)
            container.containerJoined.forEach { p -> p[::ContainerAlertImp].GAME_JOIN.send(p, element, p[{ContainerImp<ELEMENT>(it)}].containerName) }
            if (joinType === JoinType.PLAY) element.isPlaying = true
            else containerAlert.GAME_START_SPECTATING.send(element, container.containerName)
            join(container, element, joinType)
            return true
        }
        return false
    }

    private fun requestLeft(container: CONTAINER, element: ELEMENT, leftType: LeftType): Boolean {
        return container.containerJoined.contains(element)
    }

    open fun left(element: ELEMENT, container: CONTAINER, leftType: LeftType) = Unit

    fun leftGame(element: ELEMENT, leftType: LeftType): Boolean {
        if (element.component.has(element.nameSpace, ContainerElement<*>::joinedContainer.name).not()) return false
        val container = element.joinedContainer
        if (!requestLeft(container, element, leftType)) return false
        left(element, container, leftType)
        val containerAlert = element[::ContainerAlertImp]
        if (leftType === LeftType.LEFT_SERVER) {
            containerAlert.GAME_LEFT_GAME_DUE_TO_SERVER_LEFT.send(element, container.containerName)
        } else {
            container.containerJoined.forEach { p -> containerAlert.GAME_LEFT.send(p, element, p[{ContainerImp<ELEMENT>(it)}].containerName) }
        }
        element.clearTags()
        val joinedSize = container.containerJoined.filter { it.isPlaying }.size
        if (leftType.isJoinHub) {
            joinContainer(hub(), element)
        } else {
            containerHelper.left(element)
        }
        if (container.containerState !== ContainerState.STOP && joinedSize <= if (container.containerState === ContainerState.PLAY) 1 else 0) {
            stop(container, false)
        }
        return true
    }

    open fun stop(container: CONTAINER, force: Boolean, leftType: LeftType = LeftType.STOP) {
        removeContainer(container)
    }

}