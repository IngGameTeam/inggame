package io.github.inggameteam.inggame.minigame.view.gamechoosingmenu

import io.github.inggameteam.inggame.component.model.ItemModel
import io.github.inggameteam.inggame.component.wrapper.Wrapper

interface GameChoosingMenu : Wrapper {

    val title: String
    val lines: Int
    val icons: HashMap<String, ItemModel>

}