package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.inggameteam.inggame.component.model.ItemModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface GameChoosingMenu : Wrapper {

    val title: String
    val lines: Int
    val icons: HashMap<String, ItemModel>

}

class GameChoosingMenuImp(wrapper: Wrapper) : GameChoosingMenu, SimpleWrapper(wrapper) {
    override val title: String by nonNull
    override val lines: Int by nonNull
    override val icons: HashMap<String, ItemModel> by nonNull

}