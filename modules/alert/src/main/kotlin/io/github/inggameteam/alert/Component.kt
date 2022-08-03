package io.github.inggameteam.alert

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.ListWithToString
import io.github.inggameteam.utils.LocationWithoutWorld
import io.github.inggameteam.yaml.YamlUtil
import io.github.inggameteam.utils.listWithToString
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
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

    val location:   LangDir<LocationWithoutWorld>
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
        return run { if (test(getter(this@CompDir)) !== null) this@CompDir else null }
            .run { this?: parents.firstOrNull { test(getter(it)) !== null } }
    }

    fun <T> langComp(getter: (CompDir) -> LangDir<T>, key: String, lang: String): CompDir {
        return langCompOrNull(getter, key, lang)
            .apply { assertNotNull(this, "'$name, $parents' comp language $lang ${getter(this@CompDir).name} $key does not exist") }!!
    }

    fun <T> getLangComp(getter: (CompDir) -> LangDir<T>, key: String, lang: String) =
        langComp(getter, key, lang).run(getter).comp(key, lang)

    fun <T> getLangCompOrNull(getter: (CompDir) -> LangDir<T>, key: String, lang: String) =
        langCompOrNull(getter, key, lang)?.run(getter)?.comp(key, lang)


    fun <T> compOrNull(getter: (CompDir) -> CompFile<T>, key: String): CompDir? {
        fun test(comp: CompFile<T>) = comp.getOrNull(key)
        return run { if (test(getter(this@CompDir)) !== null) this@CompDir else null }
            .run { this?: parents.firstOrNull { test(getter(it)) !== null } }
    }

    fun <T> comp(getter: (CompDir) -> CompFile<T>, key: String): CompDir {
        return compOrNull(getter, key)
            .apply { assertNotNull(this, "'$name, $parents' comp ${getter(this@CompDir).name} $key does not exist") }!!
    }

    fun <T> getComp(getter: (CompDir) -> CompFile<T>, key: String) = comp(getter, key).run(getter)[key]
    fun <T> getCompOrNull(getter: (CompDir) -> CompFile<T>, key: String) = compOrNull(getter, key)?.run(getter)?.get(key)



    fun double(key: String): Double = getComp(CompDir::double, key)
    fun int(key: String): Int = getComp(CompDir::int, key)
    fun location(key: String, lang: String): LocationWithoutWorld = getLangComp(CompDir::location, key, lang)
    fun string(key: String, lang: String): String = getLangComp(CompDir::string, key, lang)
    fun item(key: String, lang: String): ItemStack = getLangComp(CompDir::item, key, lang)
    fun inventory(key: String, lang: String): Inventory = getLangComp(CompDir::inventory, key, lang)
    fun alert(key: String, lang: String): Alert<GPlayer> = getLangComp(CompDir::alert, key, lang)
    fun stringList(key: String, lang: String): MutableList<String> = getLangComp(CompDir::stringList, key, lang)

    fun hasDouble(key: String) = compOrNull(CompDir::double, key) !== null
    fun hasInt(key: String) = compOrNull(CompDir::int, key) !== null
    fun hasLocation(key: String, lang: String) = langCompOrNull(CompDir::location, key, lang) !== null
    fun hasString(key: String, lang: String) = langCompOrNull(CompDir::string, key, lang) !== null
    fun hasItem(key: String, lang: String) = langCompOrNull(CompDir::item, key, lang) !== null
    fun hasInventory(key: String, lang: String) = langCompOrNull(CompDir::inventory, key, lang) !== null
    fun hasAlert(key: String, lang: String) = langCompOrNull(CompDir::alert, key, lang) !== null
    fun hasStringList(key: String, lang: String) = langCompOrNull(CompDir::stringList, key, lang) !== null

    fun doubleOrNull(key: String) = getCompOrNull(CompDir::double, key)
    fun intOrNull(key: String) = getCompOrNull(CompDir::int, key)
    fun locationOrNull(key: String, lang: String) = getLangCompOrNull(CompDir::location, key, lang)
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

