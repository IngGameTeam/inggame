package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.mongodb.Model
import io.github.inggameteam.inggame.utils.ColorUtil.getColoredString
import org.bson.codecs.pojo.annotations.BsonExtraElements
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

@Model
class InventoryModel(
    @BsonExtraElements
    var map: HashMap<String, Any>,
    var items: ArrayList<ItemStackModel?>
) {

    constructor(inventory: Inventory) : this(HashMap(), ArrayList()) {
        setInventory(inventory)
    }

    private lateinit var cachedInventory: Inventory

    fun getInventory() = if (::cachedInventory.isInitialized) cachedInventory else {
        cachedInventory = newInventory()
        cachedInventory
    }

    fun setInventory(inventory: Inventory) {
        val contents = inventory.contents
            .map { it?.run { ItemStackModel(HashMap()).apply { setItem(it) } } }.run(::ArrayList)
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
        items.forEachIndexed { index, item -> item?.run { inven.setItem(index, item.getItemStack()) } }
        return inven
    }

}