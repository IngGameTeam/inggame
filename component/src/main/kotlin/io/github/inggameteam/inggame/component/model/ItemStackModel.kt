package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack


@Model
class ItemStackModel(
    var itemString: String? = null,
) {

    constructor(itemStack: ItemStack) : this() {
        setItemStack(itemStack)
    }

    @BsonIgnore
    private lateinit var cachedItemStack: ItemStack

    fun getItemStack() = if (::cachedItemStack.isInitialized) cachedItemStack else {
        loadItemStack()
        cachedItemStack
    }

    private fun loadItemStack() {
        cachedItemStack = newItemStack()
    }

    @Suppress("DEPRECATION")
    private fun newItemStack(): ItemStack {
        val itemStack = itemString?.run { YamlConfiguration.loadConfiguration(toString().reader()).getItemStack("_")
            ?: throw AssertionError("error occurred while reading serializedItem") }?: ItemStack(Material.STONE)
        val itemMeta = itemStack.itemMeta?: Bukkit.getItemFactory().getItemMeta(itemStack.type)!!
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    fun setItemStack(item: ItemStack) {
        itemString = YamlConfiguration().apply { set("_", item) }.saveToString()
        loadItemStack()
    }

    fun setName(name: String) {
        setItemStack(getItemStack().apply { itemMeta?.setDisplayName(name) })
    }

    fun setLore(lore: String) {
        setItemStack(getItemStack().apply { itemMeta?.lore = listOf(lore) })
    }

}