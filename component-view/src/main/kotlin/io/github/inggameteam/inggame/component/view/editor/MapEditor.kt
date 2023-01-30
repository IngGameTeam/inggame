package io.github.inggameteam.inggame.component.view.editor

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.EmptyComponentServiceImp
import io.github.inggameteam.inggame.component.view.model.ComponentServiceViewImp
import io.github.inggameteam.inggame.component.view.model.ElementViewImp
import io.github.inggameteam.inggame.component.view.model.ModelViewImp
import io.github.inggameteam.inggame.component.view.model.NameSpaceViewImp
import io.github.inggameteam.inggame.component.view.selector.Selector
import io.github.inggameteam.inggame.component.view.singleClass
import io.github.inggameteam.inggame.mongodb.Model
import org.bukkit.entity.Player
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.javaType

class MapEditor<T : Map<String, *>>(
    view: EditorView<T>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<T> by view {

    @Model
    class Entry<E : Any>(
        var key: String,
        var value: E
    )

    @Deprecated("ornamental", ReplaceWith("genericMap"))
    val genericMap: Entry<T>
        get() = throw AssertionError()

    @Suppress("DEPRECATION")
    override fun open(player: Player) {
        println((::genericMap.returnType.singleClass as ParameterizedType).actualTypeArguments[0] as Class<*>)
        println(::genericMap.returnType.arguments)
        CollectionSelector(ModelEditorView(
            ModelViewImp(
                ElementViewImp(NameSpaceViewImp(
                    ComponentServiceViewImp(this, EmptyComponentServiceImp("Unit")),
                    NameSpace("Unit", CopyOnWriteArraySet(), ConcurrentHashMap())), Pair(Unit, Unit)),
                ArrayList::class.createType(::genericMap.returnType.arguments)
        ), EditorViewImp(this,
            { set((it as ArrayList<Entry<*>>).map { e -> Pair(e.key, e.value) }.toMap() as T) },
            { get()?.entries?.map { Entry(it.key, it.value!!) } })), previousSelector)
            .open(player)
    }


}