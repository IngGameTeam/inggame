package io.github.inggameteam.alert

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.utils.YamlUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import kotlin.test.assertNotNull

interface CompDir {

    val plugin: AlertPlugin
    val name: String

    val double:     CompFile<Double>
    val int:        CompFile<Int>
    val location:   CompFile<LocationWithoutWorld>

    val item:       LangDir<ItemStack>
    val inventory:  LangDir<Inventory>
    val string:     LangDir<String>
    val alert:      LangDir<Alert<GPlayer>>
    val stringList: LangDir<MutableList<String>>

    val parents: List<CompDir>


    //Components > CompDir > LangComp > CompFile > Value
    //Components > CompDir > CompFile > Value

    fun comp(getter: (CompDir) -> LangDir<Alert<GPlayer>>, key: String, lang: String = plugin.defaultLanguage): CompDir {
        fun test(comp: LangDir<Alert<GPlayer>>) = comp.getOrNull(lang)?.getOrNull(key)
        return parents.firstOrNull { test(getter(it)) !== null }
            .run { this?: run { if (test(getter(this@CompDir)) !== null) this@CompDir else this } }
            .apply { assertNotNull(this, "'$name, $parents' comp language $lang ${getter(this@CompDir).name} $key does not exist") }!!
    }

    fun alert(key: String, lang: String) = comp({ it.alert }, key, lang).alert.comp(key, lang)

    fun send(key: String, t: GPlayer, vararg args: Any) =
        alert(key, t.lang(plugin)).send(t, *args)
    fun send(alert: String, t: Collection<GPlayer>, vararg args: Any) =
        t.forEach { alert(alert, it.lang(plugin)).send(plugin.console, it, *args) }

    fun alert(enum: Enum<*>, lang: Enum<*>) =
        alert(enum.name, lang.name)
    fun send(alert: Enum<*>, t: GPlayer, vararg args: Any) =
        send(alert.name, t, *args)
    fun send(alert: Enum<*>, t: Collection<GPlayer>, vararg args: Any) =
        t.forEach { send(alert.name, it, *args) }

}

interface Comp<T> : Map<String, T> {
    val name: String
    override fun get(key: String): T
    fun getOrNull(key: String): T?
}

abstract class CompImpl<T> : HashMap<String, T>(), Comp<T> {
    override fun getOrNull(key: String) = super.get(key)
    abstract override fun get(key: String): T
}

abstract class CompFile<T>(override val name: String) : CompImpl<T>() {
    override operator fun get(key: String) = getOrNull(key)
        .apply { assertNotNull(this, "$name $key does not exist") }!!
    protected val fileName get() = "$name.yml"
}


class LangDir<T>(file: File, override val name: String, init: (File, String) -> CompFile<T>) : CompImpl<CompFile<T>>() {
    init { File(file, name).listFiles()?.forEach { put(it.nameWithoutExtension, init(it, name)) } }
    override operator fun get(key: String) = getOrNull(key)
        .apply { assertNotNull(this, "language $key does not exist") }!!
    fun comp(key: String, lang: String): T = this[lang][key]
}

class LocationComp(file: File, name: String) : CompFile<LocationWithoutWorld>(name) {
    override val name get() = "location"
    init { getComponent(file,  YamlUtil::location) }
}

class DoubleComp(file: File, name: String) : CompFile<Double>(name) {
    init {
        getComponent(file, ConfigurationSection::getDouble)
    }
}

class IntComp(file: File, name: String) : CompFile<Int>(name) {
    override val name get() = "int"
    init {
        getComponent(file, ConfigurationSection::getInt)
    }
}

class ItemComp(file: File, name: String) : CompFile<ItemStack>(name) {
    override val name get() = "item"
    init {getComponent(file, YamlUtil::item)}
}

class InventoryComp(file: File, name: String, item: CompFile<ItemStack>?) : CompFile<Inventory>(name) {
    override val name get() = "inventory"
    init { getComponent(file) { conf -> YamlUtil.inventory(conf, item?: HashMap()) } }
}

class StringComp(file: File, name: String) : CompFile<String>(name) {
    override val name get() = "string"
    init {
        getComponent(file, YamlUtil::string)
    }
}

class AlertComp(file: File, name: String) : CompFile<Alert<GPlayer>>(name) {
    override val name get() = "alert"

    init {
        getComponent(file, AlertYamlSerialize::alert)
    }
}

class StringListComp(file: File, name: String) : CompFile<MutableList<String>>(name) {
    override val name get() = "stringlist"
    init {
        getComponent(file, ConfigurationSection::getStringList)
    }
}

fun <T> HashMap<String, T>.getComponent(file: File, function: (ConfigurationSection, String) -> T) {
    val sections = YamlUtil.getSections(file)
    sections.getKeys(false).forEach { this[it] = function(sections, it!!)!! }
}

fun <T> HashMap<String, T>.getComponent(file: File, function: (ConfigurationSection) -> T) {
    val sections = YamlUtil.getSections(file)
    sections.getKeys(false).forEach { s -> this[s] = function(sections.getConfigurationSection(s)!!) }
}

class CompDirImpl(override val plugin: AlertPlugin, file: File, override val parents: List<CompDir>) : CompDir, PluginHolder<AlertPlugin> {
    override val name: String = file.nameWithoutExtension
    override val double = DoubleComp(file, "double")
    override val int = IntComp(file, "int")
    override val location = LocationComp(file, "location")
    override val item = LangDir(file, "item", ::ItemComp)
    override val inventory = LangDir(file, "inventory") { file, name -> InventoryComp(file, name, item[name]) }
    override val string = LangDir(file, "string", ::StringComp)
    override val alert = LangDir(file, "alert", ::AlertComp)
    override val stringList = LangDir(file, "stringlist", ::StringListComp)
}

const val UNKNOWN = "unknown"
class Components(override val plugin: AlertPlugin) : HashMap<String, CompDir>(), PluginHolder<AlertPlugin>, Comp<CompDir> {

    init {
        val orders = ArrayList<String>()
        val cacheParentMap = HashMap<String, List<String>>()
        plugin.dataFolder.listFiles(File::isDirectory)?.forEach {
            val index = orders.size
            val fileName = it.name
            if (!orders.contains(fileName)) orders.add(fileName)
            YamlConfiguration.loadConfiguration(File(it, "config.yml")).getStringList("parents")
                .apply { cacheParentMap[fileName] = this }
                .forEach { pare -> if (!orders.contains(fileName)) orders.add(index, pare) }
        }
        orders.forEach {
            this[it] = CompDirImpl(plugin, File(plugin.dataFolder, it), cacheParentMap[it]!!.map { pare -> this[pare] })
        }
    }

    fun <T> langComp(getter: (CompDir) -> Comp<CompFile<T>>, lang: String, vararg components: String): CompDir {
        var type = UNKNOWN
        components.mapNotNull { getOrNull(it) }.forEach {
            getter(it).apply { type = name }.getOrNull(lang)?.getOrNull(name).apply { if (this !== null) return it }
        }
        assertNotNull(null, "language $lang $type $name '$components' directories does not exist")
    }

    override val name get() = plugin.name

    override operator fun get(key: String) = super.get(key)
        .apply { assertNotNull(this, "component $key does not exist") }!!

    override fun getOrNull(key: String) = super.get(key)
}
