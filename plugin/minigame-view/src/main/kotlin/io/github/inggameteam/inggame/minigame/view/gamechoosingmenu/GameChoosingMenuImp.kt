package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.inggameteam.inggame.component.model.ItemModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper

class GameChoosingMenuImp(wrapper: Wrapper) : GameChoosingMenu, SimpleWrapper(wrapper) {
    override val title: String by nonNull
    override val lines: Int by nonNull
    override val icons: HashMap<String, ItemModel> by nonNull

}