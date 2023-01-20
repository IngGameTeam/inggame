package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.view.model.FieldView
import io.github.inggameteam.inggame.component.view.singleClass
import kotlin.reflect.KMutableProperty

@Suppress("UNCHECKED_CAST")
interface FieldEditor<T : Any> : EditorView<T>, FieldView {

    @Suppress("DEPRECATION")
        private fun getOrNewInstance() =
        try { componentService[nameSpace.name, element.first, model.singleClass.kotlin] as T }
        catch (_: Throwable) { model.singleClass.newInstance()
            .apply { componentService.set(nameSpace.name, element.first, this) } as T }

    override val get: () -> T?
        get() = { getOrNewInstance().run { aField.getter.call(this) as? T } }

    override val set: (T) -> Unit
        get() = { getOrNewInstance().run { (aField as KMutableProperty<*>).setter.call(this, it) } }

}