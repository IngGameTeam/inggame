package io.github.inggameteam.alert

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ListWithToString
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.utils.listWithToString
import io.github.inggameteam.yaml.YamlUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File
import kotlin.test.assertNotNull

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
    override val location = LangDir(file, "location", ::LocationComp)
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
        val cacheParentMap = HashMap<String, ArrayList<String>>()
        val cacheConfigMap = HashMap<String, FileConfiguration>()

        plugin.dataFolder.listFiles(File::isDirectory)?.forEach {
            val fileName = it.name
            cacheConfigMap[fileName] = YamlConfiguration.loadConfiguration(File(it, "config.yml")).apply {
                cacheParentMap[fileName] = ArrayList(getStringList("parents"))
            }
        }

        cacheParentMap.forEach { (name, pare) ->
            ArrayList(pare).forEach {
                cacheParentMap[name]?.addAll(cacheParentMap[it]!!)
            }
        }

        val orders = ArrayList<String>()
        val isDebug = plugin.config.getBoolean("debug")
        cacheConfigMap.keys.forEach { fileName ->
            cacheParentMap[fileName]?.apply {
                var ind = 0
                forEach { pare ->
                    val indexOf = orders.indexOf(pare)
                    if (indexOf != -1 && ind <= indexOf) ind = indexOf + 1
                }
                orders.add(ind, fileName)
            }
        }
        orders.forEach {
            if (isDebug) println("Loading $it components...")
            this[it] = CompDirImpl(plugin, File(plugin.dataFolder, it), cacheParentMap[it]!!.map { pare -> this[pare] }.listWithToString())
            if (isDebug) println("Components $it Loaded")
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