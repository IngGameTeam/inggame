package io.github.inggameteam.inggame.downloader

import org.apache.commons.io.FileUtils
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.util.*

fun download(plugin: Plugin) {
    val configFile = File(plugin.dataFolder, "downloader.yml")
    val config = YamlConfiguration.loadConfiguration(configFile)
    config.getKeys(false).forEach {
        val section = config.getConfigurationSection(it)!!
        val token = section.getString("token")
        val url = section.getString("url")?: return assert(false) { "$it url does not exist" }
        val destiny = section.getString("destiny")?: return assert(false) { "$it destiny does not exist" }
        val destinyFile = File(destiny)
        plugin.logger.info("Downloading ${destinyFile.name}")
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "$token")
        val inputStream = connection.inputStream
        if (token !== null) FileUtils.copyInputStreamToFile(inputStream, destinyFile);
        plugin.logger.info("Downloaded  ${destinyFile.name}")
    }


}
