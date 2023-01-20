package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.utils.ColorUtil.getColoredString
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonExtraElements
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType


@Model
class ItemStackModel(
    var itemString: String? = null,
) {

    constructor(itemStack: ItemStack?) : this() {
        setItem(itemStack?: return)
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

    fun setItem(item: ItemStack) {
        itemString = YamlConfiguration().apply { set("_", item) }.saveToString()
        loadItemStack()
    }

    fun setName(name: String) {
        setItem(getItemStack().apply { itemMeta?.setDisplayName(name) })
    }

    fun setLore(lore: String) {
        setItem(getItemStack().apply { itemMeta?.lore = listOf(lore) })
    }

}