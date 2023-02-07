package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.utils.Model
import io.github.inggameteam.inggame.utils.ColorUtil.getColoredString
import org.bson.codecs.pojo.annotations.BsonExtraElements
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

@Model
class InventoryModel(
    @BsonExtraElements
    var map: HashMap<String, Any>,
    var items: ArrayList<ItemModel?>
) {

    constructor(inventory: Inventory) : this(HashMap(), ArrayList()) {
        setInventory(inventory)
    }

    @BsonIgnore
    private lateinit var cachedInventory: Inventory

    fun getInventory() = if (::cachedInventory.isInitialized) cachedInventory else {
        cachedInventory = newInventory()
        cachedInventory
    }

    fun setInventory(inventory: Inventory) {
        val contents = inventory.contents
            .map { it?.run { ItemModel(null).apply { itemStack = it } } }.run(::ArrayList)
        if (inventory.type === InventoryType.CHEST) {
            map["type"] = inventory.maxStackSize
        } else map["type"] = inventory.type.name
        items = contents
    }


    fun newInventory(): Inventory {
        val title = if (map.containsKey("title")) map.getColoredString("title") else null
        val type = map["type"]
        val inven = if (type is Int) {
            if (title === null) Bukkit.createInventory(null, type)
            else Bukkit.createInventory(null, type, title)
        } else if (type is String) {
            val inventoryType = InventoryType.valueOf(type)
            if (title === null) Bukkit.createInventory(null, inventoryType)
            else Bukkit.createInventory(null, inventoryType, title)
        } else Bukkit.createInventory(null, InventoryType.PLAYER)
        items.forEachIndexed { index, item -> item?.run { inven.setItem(index, item.itemStack) } }
        return inven
    }

}