package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack


@Model
class ItemStackModel(private var itemString: String?) {

//    @BsonIgnore
//    private var cacfffhedItemStack: ItemStack? = null

    @get:BsonIgnore
    @set:BsonIgnore
    var itemStack: ItemStack
        set(value) {
            itemString = YamlConfiguration().apply { set("_", value) }.saveToString().replace("\n", "\\n")
            loadItemStack()
        }
        get() /* =if (cacfffhedItemStack !== null) cacfffhedItemStack!! else*/ {
            loadItemStack()
//            cacfffhedItemStack!!
            return newItemStack()
        }

    private fun loadItemStack() {
//        cacfffhedItemStack = newItemStack()
    }

    private fun newItemStack(): ItemStack {
        val itemStack = itemString?.replace("\\n", "\n")?.run { YamlConfiguration.loadConfiguration(reader()).getItemStack("_")
            ?: throw AssertionError("error occurred while reading serializedItem") }?: ItemStack(Material.AIR)
        val itemMeta = itemStack.itemMeta?: Bukkit.getItemFactory().getItemMeta(itemStack.type)
        itemStack.itemMeta = itemMeta
        return itemStack
    }



    fun setName(name: String) {
        itemStack = (itemStack.apply { itemMeta?.setDisplayName(name) })
    }

    fun setLore(lore: String) {
        itemStack = (itemStack.apply { itemMeta?.lore = listOf(lore) })
    }

    override fun toString(): String {
        return itemString?: "null"
    }

}