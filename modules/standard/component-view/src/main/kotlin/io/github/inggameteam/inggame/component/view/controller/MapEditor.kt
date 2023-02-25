package io.github.inggameteam.inggame.component.view.controller

import io.github.inggameteam.inggame.component.view.entity.ModelView
import io.github.inggameteam.inggame.component.view.entity.createEmptyModelView
import io.github.inggameteam.inggame.utils.Model
import org.bukkit.entity.Player
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

class MapEditor<T : Map<String, *>>(
    val view: EditorView<T>,
    override val previousSelector: Selector<*>? = null,
) : Editor, EditorView<T> by view {

    @Model
    class Entry<E : Any>(
        var key: String?,
        var value: E?
    ) {
        override fun hashCode(): Int {
            return key?.hashCode()?: super.hashCode()
        }

        override fun toString(): String {
            return "$key=$value"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Entry<*>

            if (key != other.key) return false

            return true
        }
    }

    override fun open(player: Player) {
        CollectionSelector(ModelEditorView(
            createEmptyModelView(view,
                ArrayList::class.createType(listOf(
                    KTypeProjection(KVariance.OUT, Entry::class.createType(listOf((view as ModelView).model.arguments[1]))),
                ))
            ), EditorViewImp(this,
            { try { (it as ArrayList<Entry<*>>).run {
                if (any { e -> e.key === null || e.value === null }) null else this
            }?.associate { e -> Pair(e.key, e.value) }?.toMap()?.run { HashMap(this) }?.run { set(this as T) } }
            catch (_: Throwable) { } },
            { try { get()?.entries?.mapNotNull { Entry(it.key, it.value?: return@mapNotNull null) }?.run(::ArrayList) }
            catch (e: Throwable) { e.printStackTrace() } })), previousSelector)
            .open(player)
    }


}