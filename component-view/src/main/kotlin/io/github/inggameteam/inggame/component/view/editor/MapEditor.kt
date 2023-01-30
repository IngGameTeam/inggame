package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.EmptyComponentServiceImp
import io.github.inggameteam.inggame.component.view.model.*
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.singleClass
import io.github.inggameteam.inggame.mongodb.Model
import org.bukkit.entity.Player
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.full.createType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

class MapEditor<T : Map<String, *>>(
    val view: EditorView<T>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<T> by view {

    @Model
    class Entry<E : Any>(
        var key: String,
        var value: E
    )

    private val genericType get() =
        (((view as ModelView).model.javaType as ParameterizedType).actualTypeArguments[1] as Class<*>).kotlin.starProjectedType

    @Suppress("DEPRECATION")
    override fun open(player: Player) {

        CollectionSelector(ModelEditorView(
            ModelViewImp(
                ElementViewImp(NameSpaceViewImp(
                    ComponentServiceViewImp(this, EmptyComponentServiceImp("Unit")),
                    NameSpace("Unit", CopyOnWriteArraySet(), ConcurrentHashMap())), Pair(Unit, Unit)),
                genericType
        ), EditorViewImp(this,
            { set((it as ArrayList<Entry<*>>).map { e -> Pair(e.key, e.value) }.toMap() as T) },
            { get()?.entries?.map { Entry(it.key, it.value!!) } })), previousSelector)
            .open(player)
    }


}