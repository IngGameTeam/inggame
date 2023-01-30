package io.github.inggameteam.inggame.minigame.wrapper.game

import io.github.inggameteam.inggame.component.PropWrapper
import io.github.inggameteam.inggame.component.delegate.Wrapper
import io.github.inggameteam.inggame.component.model.Alert
import io.github.inggameteam.inggame.component.model.InventoryModel
import io.github.inggameteam.inggame.component.model.ItemModel

@PropWrapper
interface GameAlert : Wrapper {

    val GAME_ALREADY_JOINED                     : Alert
    val GAME_CANNOT_JOIN_DUE_TO_STARTED         : Alert
    val GAME_CANNOT_JOIN_PLAYER_LIMITED         : Alert
    val GAME_JOIN                               : Alert
    val GAME_START_SPECTATING                   : Alert
    val GAME_LEFT_GAME_DUE_TO_SERVER_LEFT       : Alert
    val GAME_LEFT                               : Alert
    val GAME_START_CANCELLED_DUE_TO_PLAYERLESS  : Alert
    val GAME_START_COUNT_DOWN                   : Alert
    val GAME_START                              : Alert
    val testItem : ItemModel
    val testInventory: InventoryModel
    val testArrayList: ArrayList<Alert>
    val testHashSet: HashSet<Alert>
    val testHashMap: HashMap<String, ItemModel>


}