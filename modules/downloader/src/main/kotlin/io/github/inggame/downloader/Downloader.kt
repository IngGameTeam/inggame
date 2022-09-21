package io.github.inggameteam.downloader

import org.apache.commons.io.FileUtils
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.net.URL

fun download(plugin: Plugin) {
    val configFile = File(plugin.dataFolder, "downloader.yml")
    val config = YamlConfiguration.loadConfiguration(configFile)
    config.getKeys(false).forEach {
        val section = config.getConfigurationSection(it)!!
        val url = section.getString("url")?: return assert(false) { "$it url does not exist" }
        val destiny = section.getString("destiny")?: return assert(false) { "$it destiny does not exist" }
        val destinyFile = File(destiny)
        plugin.logger.info("Downloading ${destinyFile.name}")
        FileUtils.copyURLToFile(URL(url), destinyFile)
        plugin.logger.info("Downloaded  ${destinyFile.name}")
    }
}
