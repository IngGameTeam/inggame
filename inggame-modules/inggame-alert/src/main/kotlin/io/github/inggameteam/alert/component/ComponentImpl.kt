package io.github.inggameteam.alert.component

import io.github.inggameteam.alert.AlertYamlSerialize
import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.utils.YamlUtil
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.io.File
import java.lang.Exception
import kotlin.test.assertTrue

typealias Comp<T> = HashMap<String, T>

interface Component {
    val plugin: IngGamePlugin
    val item: Comp<ItemStack>
    val location: Comp<LocationWithoutWorld>
    val inventory: Comp<Inventory>
    val double: Comp<Double>
    val int: Comp<Int>
    val string: Comp<String>
    val alert: Comp<Alert<Player>>
    val array: Comp<MutableList<String>>

    fun alert(name: String) = alert[name].apply { if (this === null) assertTrue(false, "alert $name does not exist") }!!
    fun alert(enum: Enum<*>) = alert(enum.name)
    fun send(alert: String, t: Player, vararg args: Any) = alert(alert).send(plugin.console, t, *args)
    fun send(alert: String, t: List<Player>, vararg args: Any) = t.forEach { alert(alert).send(plugin.console, it, *args) }
    fun send(alert: Enum<*>, t: Player, vararg args: Any) = send(alert.name, t, *args)
    fun send(alert: Enum<*>, t: List<Player>, vararg args: Any) = t.forEach { send(alert.name, it, *args) }

    companion object {
        fun saveResources(plugin: Plugin, vararg fileNames: String) =
            fileNames
                .filter { !File(plugin.dataFolder, it).exists() }
                .forEach {
                    try { plugin.saveResource(it, false) }
                    catch (_: Exception) { }
                }
    }
}

class ComponentImpl(override val plugin: IngGamePlugin, file: File, ) : Component {
    init { file.mkdirs() }
    override val item = YamlUtil.getComponent(File(file, "item.yml"),  YamlUtil::item)
    override val location = YamlUtil.getComponent(File(file, "location.yml"),  YamlUtil::location)
    override val inventory = YamlUtil.getComponent(File(file, "inventory.yml")) { conf -> YamlUtil.inventory(conf, item)}
    override val double = YamlUtil.getComponent(File(file, "double.yml")) { conf, path -> conf.getDouble(path)}
    override val int = YamlUtil.getComponent(File(file, "int.yml")) { conf, path -> conf.getInt(path)}
    override val string = YamlUtil.getComponent(File(file, "string.yml")) { conf, path -> YamlUtil.string(conf, path)}
    override val alert = YamlUtil.getComponent(File(file, "alert.yml"), AlertYamlSerialize::alert)
    override val array = YamlUtil.getComponent(File(file, "array.yml")) { conf, path -> conf.getStringList(path).toMutableList() }

}
