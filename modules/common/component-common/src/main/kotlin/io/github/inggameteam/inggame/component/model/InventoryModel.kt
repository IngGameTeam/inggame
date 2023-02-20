package io.github.inggameteam.inggame.component.model

import io.github.inggameteam.inggame.utils.ColorUtil.getColoredString
import io.github.inggameteam.inggame.utils.Model
import org.bson.codecs.pojo.annotations.BsonExtraElements
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

@Model
class InventoryModel(
    @BsonExtraElements
    private var _map: HashMap<String, Any>?,
    private var _items: ArrayList<ItemModel?>?
) {

    var map
        get() = _map?: run { HashMap<String, Any>().also { _map = it } }
        set(value) { _map = value }
    var items
        get() = _items?: run { ArrayList<ItemModel?>().also { _items = it } }
        set(value) { _items = value }

    constructor() : this(Bukkit.createInventory(null, InventoryType.CHEST))
    constructor(inventory: Inventory) : this(HashMap(), ArrayList()) {
        this.inventory = inventory
    }

    @BsonIgnore
    private lateinit var cachedInventory: Inventory

    @get:BsonIgnore
    @set:BsonIgnore
    var inventory: Inventory
        get() = if (::cachedInventory.isInitialized) cachedInventory else {
            cachedInventory = newInventory()
            cachedInventory
        }
        set(inventory) {
            val contents = inventory.contents
                .map { it?.run { ItemModel(null).apply { itemStack = it } } }.run(::ArrayList)
            if (inventory.type === InventoryType.CHEST) {
                map["type"] = inventory.maxStackSize
            } else map["type"] = inventory.type.name
            items = contents
        }

    fun setTitle(title: String) {
        map["title"] = title
    }

    fun getTitle(): String {
        return map["title"]!!.toString()
    }

    fun setType(type: String) {
        map["type"] = type
    }

    fun getType(): String {
        return map["type"]!!.toString()
    }

    @BsonIgnore
    private fun newInventory(): Inventory {
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

    override fun toString(): String {
        return "A Inventory"
    }

    companion object {
        fun Inventory.toInventoryModel(): InventoryModel = InventoryModel(this)
    }

}