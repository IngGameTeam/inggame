package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.editor.Editor
import io.github.inggameteam.inggame.component.view.model.ModelView
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.lang.reflect.Modifier
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.kotlinProperty

typealias Field = KProperty<*>
class ModelFieldSelector(
    private val modelView: ModelView,
    override val parentSelector: Selector<*>? = null
) : ModelView by modelView, Selector<Field>, Editor {

    override val previousSelector: Selector<*>? get() = parentSelector

    override val elements: Collection<Field>
        get() = model.declaredMemberProperties
            .filter { it.findAnnotation<BsonIgnore>() === null }
            .filter { it.visibility === KVisibility.PUBLIC }

    override fun select(t: Field, event: InventoryClickEvent) {
        TODO()
    }

    override fun transform(t: Field) = createItem(Material.OAK_PLANKS, t.name)

}