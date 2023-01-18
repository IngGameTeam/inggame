package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.View
import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.entity.Player

interface Editor : View {

    val previousSelector: Selector<*>?

    fun open(player: Player)

    val editor: String get() = javaClass.simpleName


}