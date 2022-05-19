package io.github.inggameteam.utils

import io.github.inggameteam.utils.ColorUtil.color
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import java.io.File

object YamlUtil {
    fun getSections(file: File): ConfigurationSection = YamlConfiguration.loadConfiguration(file)

    fun item(conf: ConfigurationSection): ItemStack {
        var itemStack = ItemStack(Material.AIR)
        try {
            if (conf.isSet("item"))
                itemStack = ItemUtil.safeClone(conf.getItemStack("item"))
            if (conf.isSet("type"))
                itemStack.type = Material.valueOf(string(conf, "type"))
            if (conf.isSet("amount"))
                itemStack.amount = conf.getInt("amount")
            val itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.type)!!
            if (conf.isSet("name"))
                itemMeta.setDisplayName(string(conf, "name"))
            if (conf.isSet("lore"))
                itemMeta.lore = string(conf, "lore").split("\n").let { it.subList(0, it.size-1) }
            if (conf.isSet("hide"))
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS)
            if (conf.isSet("unbreak"))
                itemMeta.isUnbreakable = true
            if (conf.isSet("data"))
                itemStack.data!!.data = conf.getInt("data").toByte()
            if (conf.isSet("color")) {
                (itemMeta as LeatherArmorMeta).setColor(
                    if (conf.isString("color")) {
                        ColorUtil.hex2Rgb(conf.getString("color")!!)
                    } else Color.fromRGB(
                        conf.getInt("color.R"),
                        conf.getInt("color.G"),
                        conf.getInt("color.B"),
                    ))
            }
            if (conf.isSet("potion"))
                (itemMeta as PotionMeta).apply {
                    basePotionData = PotionData(PotionType.valueOf(conf.getString("potion")!!))
                }
            itemStack.itemMeta = itemMeta
            if (conf.isSet("enchants")) {
                val section = conf.getConfigurationSection("enchants")!!
                section.getKeys(false).forEach { k ->
                    itemStack.addUnsafeEnchantment(Enchantment.getByName(k)!!, section.getInt(k))
                }
            }
        } catch (_: Exception) {}
        return itemStack
    }
    fun location(conf: ConfigurationSection) =
        LocationWithoutWorld(
            conf.getDouble("X"),
            conf.getDouble("Y"),
            conf.getDouble("Z"),
            conf.getDouble("YAW").toFloat(),
            conf.getDouble("PITCH").toFloat(),
            conf.getString("TAG")
        )
    fun string(yaml: ConfigurationSection, path: String) = yaml.getString(path)!!.color()
    fun inventory(yaml: ConfigurationSection, itemComp: (String) -> ItemStack): Inventory {
        val title = if (yaml.isSet("title")) string(yaml, "title") else null
        val inven = if (yaml.isInt("type")) {
            val size = yaml.getInt("type")
            if (title == null) Bukkit.createInventory(null, size)
            else Bukkit.createInventory(null, size, title)
        } else if (yaml.isString("type")) {
            val type = InventoryType.valueOf(yaml.getString("type")!!)
            if (title == null) Bukkit.createInventory(null, type)
            else Bukkit.createInventory(null, type, title)
        } else defaultInventory()
        yaml.getConfigurationSection("item")?.apply {
            val items: HashMap<Int, ItemStack> = HashMap()
            getKeys(false)
                .forEach { k ->
                    items[Integer.parseInt(k)] =
                        if (isString(k)) itemComp(getString(k)!!)
                        else item(getConfigurationSection(k)!!)
                }
            items.forEach { (t, u) -> inven.setItem(t, u) }
        }
        return inven
    }
    private fun defaultInventory() = Bukkit.createInventory(null, InventoryType.PLAYER)

}
