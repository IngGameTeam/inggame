package io.github.inggameteam.alert

import io.github.inggameteam.utils.YamlUtil
import java.io.File

class AlertComponent(file: File) {
    val item = YamlUtil.getComponent(File(file, "item.yml"),  YamlUtil::item)
    val location = YamlUtil.getComponent(File(file, "location.yml"),  YamlUtil::location)
    val inventory = YamlUtil.getComponent(File(file, "inventory.yml")) { conf -> YamlUtil.inventory(conf, item)}
    val double = YamlUtil.getComponent(File(file, "double.yml")) { conf, path -> conf.getDouble(path)}
    val int = YamlUtil.getComponent(File(file, "int.yml")) { conf, path -> conf.getInt(path)}
    val string = YamlUtil.getComponent(File(file, "string.yml")) { conf, path -> YamlUtil.string(conf, path)}
    val alert = YamlUtil.getComponent(File(file, "alert.yml"), AlertYamlSerialize::alert)
    val array = YamlUtil.getComponent(File(file, "array.yml")) { conf, path -> conf.getStringList(path).toMutableList() }
}
