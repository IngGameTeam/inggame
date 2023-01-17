package io.github.inggameteam.inggame.component.view.editor

import io.github.bruce0203.gui.GuiFrame
import io.github.inggameteam.inggame.component.view.editor.model.Editor
import kotlin.reflect.KType

typealias EditorDeclaration = (Editor) -> GuiFrame

class EditorRegistry {

    val map = HashMap<KType, EditorDeclaration>()

}