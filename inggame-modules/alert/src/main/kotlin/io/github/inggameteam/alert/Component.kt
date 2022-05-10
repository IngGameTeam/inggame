package io.github.inggameteam.alert

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.alert.component.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ListWithToString
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.utils.YamlUtil
import io.github.inggameteam.utils.listWithToString
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

    val parents: ListWithToString<CompDir>

    //Components > CompDir > LangComp > CompFile > Value
    //Components > CompDir > CompFile > Value

    fun <T> langCompOrNull(getter: (CompDir) -> LangDir<T>, key: String, lang: String): CompDir? {
        fun test(comp: LangDir<T>) = comp.getOrNull(lang)?.getOrNull(key)
        return parents.firstOrNull { test(getter(it)) !== null }
            .run { this?: run { if (test(getter(this@CompDir)) !== null) this@CompDir else this } }
    }


    fun <T> langComp(getter: (CompDir) -> LangDir<T>, key: String, lang: String): CompDir {
        fun test(comp: LangDir<T>) = comp.getOrNull(lang)?.getOrNull(key)
        return langCompOrNull(getter, key, lang)
            .apply { assertNotNull(this, "'$name, $parents' comp language $lang ${getter(this@CompDir).name} $key does not exist") }!!
    }

    fun <T> getLangComp(getter: (CompDir) -> LangDir<T>, key: String, lang: String) =
        langComp(getter, key, lang).run(getter).comp(key, lang)

    fun <T> getLangCompOrNull(getter: (CompDir) -> LangDir<T>, key: String, lang: String) =
        langCompOrNull(getter, key, lang)?.run(getter)?.comp(key, lang)


    fun <T> compOrNull(getter: (CompDir) -> CompFile<T>, key: String): CompDir? {
        fun test(comp: CompFile<T>) = comp.getOrNull(key)
        return parents.firstOrNull { test(getter(it)) !== null }
            .run { this?: run { if (test(getter(this@CompDir)) !== null) this@CompDir else this } }
    }

    fun <T> comp(getter: (CompDir) -> CompFile<T>, key: String): CompDir {
        fun test(comp: CompFile<T>) = comp.getOrNull(key)
        return compOrNull(getter, key)
            .apply { assertNotNull(this, "'$name, $parents' comp ${getter(this@CompDir).name} $key does not exist") }!!
    }

    fun <T> getComp(getter: (CompDir) -> CompFile<T>, key: String) = comp(getter, key).run(getter)[key]
    fun <T> getCompOrNull(getter: (CompDir) -> CompFile<T>, key: String) = compOrNull(getter, key)?.run(getter)?.get(key)



    fun double(key: String): Double = getComp(CompDir::double, key)
    fun int(key: String): Int = getComp(CompDir::int, key)
    fun location(key: String): LocationWithoutWorld = getComp(CompDir::location, key)
    fun string(key: String, lang: String): String = getLangComp(CompDir::string, key, lang)
    fun item(key: String, lang: String): ItemStack = getLangComp(CompDir::item, key, lang)
    fun inventory(key: String, lang: String): Inventory = getLangComp(CompDir::inventory, key, lang)
    fun alert(key: String, lang: String): Alert<GPlayer> = getLangComp(CompDir::alert, key, lang)
    fun stringList(key: String, lang: String): MutableList<String> = getLangComp(CompDir::stringList, key, lang)

    fun hasDouble(key: String) = compOrNull(CompDir::double, key) !== null
    fun hasInt(key: String) = compOrNull(CompDir::int, key) !== null
    fun hasLocation(key: String) = compOrNull(CompDir::location, key) !== null
    fun hasString(key: String, lang: String) = langCompOrNull(CompDir::string, key, lang) !== null
    fun hasItem(key: String, lang: String) = langCompOrNull(CompDir::item, key, lang) !== null
    fun hasInventory(key: String, lang: String) = langCompOrNull(CompDir::inventory, key, lang) !== null
    fun hasAlert(key: String, lang: String) = langCompOrNull(CompDir::alert, key, lang) !== null
    fun hasStringList(key: String, lang: String) = langCompOrNull(CompDir::stringList, key, lang) !== null

    fun doubleOrNull(key: String) = getCompOrNull(CompDir::double, key)
    fun intOrNull(key: String) = getCompOrNull(CompDir::int, key)
    fun locationOrNull(key: String) = getCompOrNull(CompDir::location, key)
    fun stringOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::string, key, lang)
    fun itemOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::item, key, lang)
    fun inventoryOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::inventory, key, lang)
    fun alertOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::alert, key, lang)
    fun stringListOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::stringList, key, lang)

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
}


class LangDir<T>(file: File, override val name: String, init: (File, String) -> CompFile<T>) : CompImpl<CompFile<T>>() {
    init { File(file, name).listFiles()?.forEach { put(it.nameWithoutExtension, init(it, it.nameWithoutExtension)) } }
    override operator fun get(key: String) = getOrNull(key)
        .apply { assertNotNull(this, "component $name language $key does not exist") }!!
    fun comp(key: String, lang: String): T = this[lang][key]
    fun hasComp(key: String, lang: String): Boolean = this.getOrNull(lang)?.getOrNull(key) !== null
}


class LocationComp(file: File, name: String = file.nameWithoutExtension) : CompFile<LocationWithoutWorld>(name) {
    init { getComponent(file,  YamlUtil::location) }
}

class DoubleComp(file: File, name: String = file.nameWithoutExtension) : CompFile<Double>(name) {
    init {
        getComponent(file, ConfigurationSection::getDouble)
    }
}

class IntComp(file: File, name: String = file.nameWithoutExtension) : CompFile<Int>(name) {
    init {
        getComponent(file, ConfigurationSection::getInt)
    }
}

class ItemComp(file: File, name: String = file.nameWithoutExtension) : CompFile<ItemStack>(name) {
    init {getComponent(file, YamlUtil::item)}
}

class InventoryComp(file: File, name: String = file.nameWithoutExtension, item: (String) -> ItemStack) : CompFile<Inventory>(name) {
    init { getComponent(file) { conf -> YamlUtil.inventory(conf, item) } }
}

class StringComp(file: File, name: String = file.nameWithoutExtension) : CompFile<String>(name) {
    init {
        getComponent(file, YamlUtil::string)
    }
}

class AlertComp(file: File, name: String = file.nameWithoutExtension) : CompFile<Alert<GPlayer>>(name) {
    init {
        getComponent(file, AlertYamlSerialize::alert)
    }
}

class StringListComp(file: File, name: String = file.nameWithoutExtension) : CompFile<MutableList<String>>(name) {
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

class CompDirImpl(override val plugin: AlertPlugin, file: File, override val parents: ListWithToString<CompDir>) : CompDir, PluginHolder<AlertPlugin> {
    override val name: String = file.nameWithoutExtension
    override fun toString() =  name
    override val double = DoubleComp(File(file, "double.yml"))
    override val int = IntComp(File(file, "int.yml"))
    override val location = LocationComp(File(file, "location.yml"))
    override val item = LangDir(file, "item", ::ItemComp)
    override val inventory = LangDir(file, "inventory") { file, name -> InventoryComp(file, name)
    { getLangComp(CompDir::item, it, name) } }
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
            if (!orders.contains(fileName)) orders.add(0, fileName)
            YamlConfiguration.loadConfiguration(File(it, "config.yml")).getStringList("parents")
                .apply { cacheParentMap[fileName] = this }
                .forEach { pare -> if (!orders.contains(fileName)) orders.add(index, pare) }
        }
        orders.forEach {
            this[it] = CompDirImpl(plugin, File(plugin.dataFolder, it), cacheParentMap[it]!!.map { pare -> this[pare] }.listWithToString())
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
