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
    @BsonExtraElements
    var map: HashMap<String, Any>,
) {

    constructor(itemStack: ItemStack?) : this(HashMap()) {
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
        val itemStack = map["item"]?.run { YamlConfiguration.loadConfiguration(toString().reader()).getItemStack("_")
            ?: throw AssertionError("error occurred while reading serializedItem") }?: ItemStack(Material.STONE)
        if (map.containsKey("type")) {
            val type = map.getColoredString("type")
            try {
                itemStack.type = type.run(Material::valueOf)
            } catch(_: Exception) {
                throw AssertionError("$type is not a material")
            }
        }
        val itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.type)!!
        if (map.containsKey("amount")) itemStack.amount = map["amount"] as Int
        if (map.containsKey("name")) itemMeta.setDisplayName(map.getColoredString("name"))
        if (map.containsKey("lore")) itemMeta.setDisplayName(map.getColoredString("lore"))
        if (map.containsKey("hide"))
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS)
        if (map.containsKey("unbreak"))
            itemMeta.isUnbreakable = true
        if (map.containsKey("data")) {
            val data = itemStack.data
                ?: throw AssertionError ("a unknown error occurred while deserializing yaml item configuration")
            data.data = map["data"] as Byte
        }
        if (map.containsKey("color")) {
            (map["color"] as Document).run {
                Color.fromRGB(
                    getInteger("R"),
                    getInteger("G"),
                    getInteger("B"),
                )
            }.apply { (itemMeta as LeatherArmorMeta).setColor(this) }
        }
        if (map.containsKey("potion")) {
            val potionMeta = itemMeta as PotionMeta
            try {
                potionMeta.basePotionData = PotionData(PotionType.valueOf(map.getColoredString("potion")))
            } catch (_: Exception) {
                throw AssertionError("an error occurred while read item potion configuration")
            }
        }
        if (map.containsKey("enchants")) {
            (map["enchants"] as Document).apply {
                entries.forEach {
                    val enchantment = Enchantment.getByName(it.key)
                        ?: throw AssertionError("${it.key} is not a enchants")
                    itemStack.addUnsafeEnchantment(enchantment, it.value as Int)
                }
            }
        }
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    fun setItem(item: ItemStack) {
        map["item"] = YamlConfiguration().apply { set("_", item) }.saveToString()
        loadItemStack()
    }

    fun setName(name: String) {
        map["name"] = name
        loadItemStack()
    }

    fun setLore(lore: String) {
        map["lore"] = lore
    }

}