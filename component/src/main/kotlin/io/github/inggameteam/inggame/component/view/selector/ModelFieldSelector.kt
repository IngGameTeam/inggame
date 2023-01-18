package io.github.inggameteam.inggame.component.view.selector

import io.github.inggameteam.inggame.component.view.createItem
import io.github.inggameteam.inggame.component.view.model.ElementView
import io.github.inggameteam.inggame.component.view.model.ModelView
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Modifier
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.kotlinProperty

typealias Field = KProperty<*>
class ModelFieldSelector(
    modelView: ModelView,
    override val parentSelector: Selector<*>?
) : ModelView by modelView, Selector<Field> {
    override val elements: Collection<Field>
        get() = model.java.declaredFields
            .filter { it.getAnnotation(BsonIgnore::class.java) === null }
            .filterNot { Modifier.isPrivate(it.modifiers) }
            .map { it.kotlinProperty!! }

    override fun select(t: Field, event: InventoryClickEvent) {

    }

    override fun transform(t: Field) = createItem(
        Material.OAK_PLANKS,
        t.name
    )

}