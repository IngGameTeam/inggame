package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.FieldView

class FieldEditorImp<T : Any>(fieldView: FieldView) : FieldEditor<T>, FieldView by fieldView