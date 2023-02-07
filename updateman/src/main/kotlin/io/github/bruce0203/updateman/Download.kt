package io.github.bruce0203.updateman

import io.github.inggameteam.inggame.plugman.util.PluginUtil
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import java.io.File
import java.net.URL

fun download(pluginName: String, url: String, destiny: String) {
    val destinyFile = File(destiny)
    println("Downloading ${destinyFile.name}")
    destinyFile.parentFile.mkdir()
    val pl = Bukkit.getPluginManager().getPlugin(pluginName)
    FileUtils.copyURLToFile(URL(url),
        if (pl !== null) destinyFile else File("plugins/${destinyFile.name}")
    )
    println("Downloaded  ${destinyFile.name}")
    if (pl == null) PluginUtil.load(destinyFile.nameWithoutExtension)
    else PluginUtil.reload(pl)
}