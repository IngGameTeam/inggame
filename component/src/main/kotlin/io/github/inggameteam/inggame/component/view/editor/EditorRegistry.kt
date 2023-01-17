package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrame
import io.github.inggameteam.inggame.component.view.editor.model.View
import kotlin.reflect.KType

typealias EditorDeclaration = (View) -> GuiFrame

class EditorRegistry {

    val map = HashMap<KType, EditorDeclaration>()

}