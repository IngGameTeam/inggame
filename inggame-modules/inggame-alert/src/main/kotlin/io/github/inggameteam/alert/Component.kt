package io.github.inggameteam.alert

import io.github.inggameteam.utils.YamlUtil
import org.bukkit.plugin.Plugin
import java.io.File
import java.lang.Exception

class Component(file: File) {
    init {
        file.mkdirs()
    }
    val item = YamlUtil.getComponent(File(file, "item.yml"),  YamlUtil::item)
    val location = YamlUtil.getComponent(File(file, "location.yml"),  YamlUtil::location)
    val inventory = YamlUtil.getComponent(File(file, "inventory.yml")) { conf -> YamlUtil.inventory(conf, item)}
    val double = YamlUtil.getComponent(File(file, "double.yml")) { conf, path -> conf.getDouble(path)}
    val int = YamlUtil.getComponent(File(file, "int.yml")) { conf, path -> conf.getInt(path)}
    val string = YamlUtil.getComponent(File(file, "string.yml")) { conf, path -> YamlUtil.string(conf, path)}
    val alert = YamlUtil.getComponent(File(file, "alert.yml"), AlertYamlSerialize::alert)
    val array = YamlUtil.getComponent(File(file, "array.yml")) { conf, path -> conf.getStringList(path).toMutableList() }

    companion object {
        fun saveResources(vararg fileNames: String, plugin: Plugin) =
            fileNames
                .filter { !File(plugin.dataFolder, it).exists() }
                .forEach {
                    try { plugin.saveResource(it, false) }
                    catch (_: Exception) { }
                }

    }

}
