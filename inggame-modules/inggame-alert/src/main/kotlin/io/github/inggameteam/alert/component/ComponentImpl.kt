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
import java.lang.Exception
import kotlin.collections.HashMap
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

typealias Comp<T> = HashMap<String, T>
typealias LangComp<T> = HashMap<String, HashMap<String, T>>

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

    fun alert(name: String, lang: String) =
        alert[lang].apply { assertNotNull(this, "language $lang does not exist") }!![name]
            .apply { if (this === null) assertTrue(false, "language $lang alert $name does not exist") }!!
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
    override val item = dir(File(file, "item")) { YamlUtil.getComponent(it, YamlUtil::item) }
    override val inventory = dir(File(file, "inventory"))
        { YamlUtil.getComponent(it) { conf -> YamlUtil.inventory(conf, item[it.parent]!!) } }
    override val string = dir(File(file, "string"))
        { YamlUtil.getComponent(it) { conf, path -> YamlUtil.string(conf, path) } }
    override val alert = dir(File(file, "alert"))
        { YamlUtil.getComponent(it, AlertYamlSerialize::alert) }
    override val array = dir(File(file, "array"))
        { YamlUtil.getComponent(it) { conf, path -> conf.getStringList(path).toMutableList() } }
    private fun <T> dir(dir: File, init: (File) -> HashMap<String, T>): HashMap<String, HashMap<String, T>> {
        val map = LangComp<T>()
        dir.listFiles(File::isFile)
            ?.forEach { map[it.nameWithoutExtension] = init(File(dir, it.name)) }
        return map
    }
}

/*
fun Collection<GPlayer>.receiveAll(sender: UUID, alert: Alert<GPlayer>, vararg args: Any) {
    forEach { gGPlayer -> alert.send(sender, gGPlayer, *args)}
    }
*/
