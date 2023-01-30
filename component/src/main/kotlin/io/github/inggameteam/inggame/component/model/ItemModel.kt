package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.util.*


@Model
class ItemModel(var itemString: String?) {

    @BsonIgnore
    private var cachedItemStack: ItemStack? = null

    @get:BsonIgnore
    @set:BsonIgnore
    var itemStack: ItemStack
        set(value) {
            itemString = YamlConfiguration().apply { set("_", value) }.saveToString().run { Base64.getEncoder().encodeToString(toByteArray()) }
            loadItemStack()
        }
        get() = if (cachedItemStack !== null) cachedItemStack!! else {
            loadItemStack()
            cachedItemStack!!
        }

    private fun loadItemStack() {
        cachedItemStack = newItemStack()
    }

    private fun newItemStack(): ItemStack {
        val itemStack = itemString
            ?.run { Base64.getDecoder().decode(this).run(::String) }
            ?.replace("\\n", "\n")?.run { YamlConfiguration.loadConfiguration(reader()).getItemStack("_")
            ?: throw AssertionError("error occurred while reading serializedItem") }?: ItemStack(Material.AIR)
        val itemMeta = itemStack.itemMeta?: Bukkit.getItemFactory().getItemMeta(itemStack.type)
        itemStack.itemMeta = itemMeta
        return itemStack
    }



    @BsonIgnore
    fun name(name: String) {
        itemStack = (itemStack.apply { itemMeta?.setDisplayName(name) })
    }

    @BsonIgnore
    fun lore(lore: String) {
        itemStack = (itemStack.apply { itemMeta?.lore = listOf(lore) })
    }

    override fun toString(): String {
        return itemString?: "null"
    }

    companion object {
        fun ItemStack.toItemModel(): ItemModel = ItemModel(null).apply { itemStack = this@toItemModel }
    }

}