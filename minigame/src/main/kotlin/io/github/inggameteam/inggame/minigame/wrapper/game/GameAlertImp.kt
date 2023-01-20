package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.model.InventoryModel
import io.github.inggameteam.inggame.component.model.ItemModel

@PropWrapper
class GameAlertImp(wrapper: Wrapper) : Wrapper by wrapper, GameAlert {
    override val GAME_ALREADY_JOINED: Alert by nonNull
    override val GAME_CANNOT_JOIN_DUE_TO_STARTED: Alert by nonNull
    override val GAME_CANNOT_JOIN_PLAYER_LIMITED: Alert by nonNull
    override val GAME_JOIN: Alert by nonNull
    override val GAME_START_SPECTATING: Alert by nonNull
    override val GAME_LEFT_GAME_DUE_TO_SERVER_LEFT: Alert by nonNull
    override val GAME_LEFT: Alert by nonNull
    override val GAME_START_CANCELLED_DUE_TO_PLAYERLESS: Alert by nonNull
    override val GAME_START_COUNT_DOWN: Alert by nonNull
    override val GAME_START: Alert by nonNull
    override val testItem: ItemModel by nonNull
    override val testInventory: InventoryModel by nonNull
    override val testArrayList: ArrayList<String> by nonNull
    override val testHashSet: HashSet<Alert> by nonNull

}