package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack


@Model
class ItemStackModel(private var itemString: String?) {

    constructor(itemStack: ItemStack) : this(null) {
        setItemStack(itemStack)
    }

    @BsonIgnore
    private var cachedItemStack: ItemStack? = null

    fun getItemStack() = if (cachedItemStack !== null) cachedItemStack!! else {
        loadItemStack()
        cachedItemStack!!
    }

    private fun loadItemStack() {
        cachedItemStack = newItemStack()
    }

    @Suppress("DEPRECATION")
    private fun newItemStack(): ItemStack {
        val itemStack = itemString?.run { YamlConfiguration.loadConfiguration(toString().reader()).getItemStack("_")
            ?: throw AssertionError("error occurred while reading serializedItem") }?: ItemStack(Material.AIR)
        val itemMeta = itemStack.itemMeta?: Bukkit.getItemFactory().getItemMeta(itemStack.type)
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

    override fun toString(): String {
        return itemString?: "null"
    }

}