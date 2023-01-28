package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.selector.Selector
import org.bukkit.entity.Player

class BooleanEditor(
    view: EditorView<Boolean>,
    override val previousSelector: Selector<*>? = null
) : Editor, EditorView<Boolean> by view {

    override fun open(player: Player) {
        set(get()?.not()?: true)
        previousSelector?.open(player)
    }

}