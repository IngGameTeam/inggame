package io.github.inggameteam.inggame.player.container

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.utils.ContainerState
import io.github.inggameteam.inggame.utils.JoinType
import io.github.inggameteam.inggame.utils.LeftType
import kotlin.system.measureTimeMillis

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

        if (requestedContainer.joinedPlayers.contains(element)) {
            if (sendMessage) containerAlert.GAME_ALREADY_JOINED.send(element, requestedContainer.containerName)
        } else if (requestedContainer.containerState !== ContainerState.WAIT && joinType === JoinType.PLAY) {
            if (sendMessage) containerAlert.GAME_CANNOT_JOIN_DUE_TO_STARTED.send(element, requestedContainer.containerName)
        } else if (requestedContainer.playerLimitAmount > 0
            && requestedContainer.joinedPlayers.filter { it.isPlaying }.size >= requestedContainer.playerLimitAmount
            && joinType === JoinType.PLAY
        ) {
            if (sendMessage) containerAlert.GAME_CANNOT_JOIN_PLAYER_LIMITED.send(element, requestedContainer.containerName)
        } else {
            return true
        }
        return false
    }

    open fun onJoin(container: CONTAINER, element: ELEMENT, joinType: JoinType) = Unit

    fun joinContainer(container: CONTAINER, element: ELEMENT, joinType: JoinType = JoinType.PLAY): Boolean {
        leftContainer(element, LeftType.DUE_TO_MOVE_ANOTHER)
        val containerAlert = element[::ContainerAlertImp]
        if (requestJoin(container, element, joinType, true)) {
            containerHelper.join(container, element)
            println(measureTimeMillis{
                container.joinedPlayers.forEach { p ->
                    p[::ContainerAlertImp].GAME_JOIN.send(
                        p,
                        element,
                        p.joined.containerName
                    )
                }
            })
            if (joinType === JoinType.PLAY) element.isPlaying = true
            else containerAlert.GAME_START_SPECTATING.send(element, container.containerName)
            onJoin(container, element, joinType)
            return true
        }
        return false
    }

    private fun requestLeft(container: CONTAINER, element: ELEMENT, leftType: LeftType): Boolean {
        return container.joinedPlayers.contains(element)
    }

    open fun onLeft(element: ELEMENT, container: CONTAINER, leftType: LeftType) = Unit

    fun leftContainer(element: ELEMENT, leftType: LeftType): Boolean {
        println(measureTimeMillis{ if (element.component.has(element.nameSpace, ContainerElement<*>::joined.name).not()) return false })
        val container = element.joined
        if (!requestLeft(container, element, leftType)) return false
        onLeft(element, container, leftType)
        val containerAlert = element[::ContainerAlertImp]
        if (leftType === LeftType.LEFT_SERVER) {
            containerAlert.GAME_LEFT_GAME_DUE_TO_SERVER_LEFT.send(element, container.containerName)
        } else {
            container.joinedPlayers.forEach { p -> p[::ContainerAlertImp].GAME_LEFT.send(p, element, p.joined.containerName) }
        }
        element.clearTags()
        val joinedSize = container.joinedPlayers.filter { it.isPlaying }.size
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