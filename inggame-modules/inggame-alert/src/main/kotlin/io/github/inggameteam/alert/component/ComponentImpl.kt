package io.github.inggameteam.alert.component

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.alert.AlertYamlSerialize
import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.utils.YamlUtil
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.io.File
import kotlin.test.assertNotNull

typealias Comp<T> = HashMap<String, T>
//typealias LangComp<T> = HashMap<String, HashMap<String, T>>

/*
 *----------------------------------
 *
 * 이 코드 발로 짠 거임
 *
 *----------------------------------
 *
 *
 *
 *
 */


interface Component {
    val plugin: AlertPlugin
    val double: Comp<Double>
    val int: Comp<Int>
    val location: Comp<LocationWithoutWorld>
    val item: LangComp<ItemStack>
    val inventory: LangComp<Inventory>
    val string: LangComp<String>
    val alert: LangComp<Alert<GPlayer>>
    val array: LangComp<MutableList<String>>


    fun alert(name: String, lang: String) = alert.comp(name, lang)
    fun send(alert: String, t: GPlayer, vararg args: Any) =
        alert(alert, t.lang(plugin)).send(plugin.console, t, *args)
    fun send(alert: String, t: Collection<GPlayer>, vararg args: Any) =
        t.forEach { alert(alert, it.lang(plugin)).send(plugin.console, it, *args) }

    fun alert(enum: Enum<*>, lang: Enum<*>) =
        alert(enum.name, lang.name)
    fun send(alert: Enum<*>, t: GPlayer, vararg args: Any) =
        send(alert.name, t, *args)
    fun send(alert: Enum<*>, t: Collection<GPlayer>, vararg args: Any) =
        t.forEach { send(alert.name, it, *args) }


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

class ComponentImpl(override val plugin: AlertPlugin, file: File, ) : Component {
    init { file.mkdirs() }
    override val location = YamlUtil.getComponent(File(file, "location.yml"),  YamlUtil::location)
    override val double = YamlUtil.getComponent(File(file, "double.yml")) { conf, path -> conf.getDouble(path)}
    override val int = YamlUtil.getComponent(File(file, "int.yml")) { conf, path -> conf.getInt(path)}
    override val item = dir(plugin, File(file, "item")) { YamlUtil.getComponent(it, YamlUtil::item) }
    override val inventory = dir(plugin, File(file, "inventory"))
        { YamlUtil.getComponent(it) { conf -> YamlUtil.inventory(conf, item[it.parent]!!) } }
    override val string = dir(plugin, File(file, "string"))
        { YamlUtil.getComponent(it) { conf, path -> YamlUtil.string(conf, path) } }
    override val alert = dir(plugin, File(file, "alert"))
        { YamlUtil.getComponent(it, AlertYamlSerialize::alert) }
    override val array = dir(plugin, File(file, "array"))
        { YamlUtil.getComponent(it) { conf, path -> conf.getStringList(path).toMutableList() } }
    private fun <T> dir(plugin: AlertPlugin, dir: File, init: (File) -> HashMap<String, T>): LangComp<T> {
        val map = LangComp<T>(dir.name, plugin, HashMap())
        dir.listFiles(File::isFile)
            ?.forEach { map[it.nameWithoutExtension] = init(File(dir, it.name)) }
        return map
    }
}

class LangComp<T>(
    private val type: String,
    val plugin: AlertPlugin, map: HashMap<String, HashMap<String, T>>
) : HashMap<String, HashMap<String, T>>(map) {
    fun comp(key: String, lang: String = plugin.defaultLanguage) = get(lang)?.get(key)
        .apply { assertNotNull(this, "language $lang $type $key does not exist") }!!
    fun comp(key: String, player: GPlayer) = comp(key, plugin[player].lang(plugin))

}