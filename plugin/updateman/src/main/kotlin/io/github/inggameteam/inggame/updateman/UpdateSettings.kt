package io.github.inggameteam.inggame.updateman

import org.bukkit.Bukkit
import java.io.File
import java.io.File.separator

data class UpdateSettings(
    val pluginName: String,
    val gitUrl: String,
    val branchName: String,
    val outputPath: String,
    val bashCmd: String
) {

    val outputFileName get() = outputFile.absoluteFile.name
    val backupFile get() = File(backupDir, outputFileName)
    val destinyFile get() = File("plugins", outputFileName)
    val outputFile get() = File(gitDir, outputPath)
    val pluginOrNull get() = Bukkit.getPluginManager().getPlugin(pluginName)
    val plugin get() = pluginOrNull!!
    val gitDir get() = File(plugin.dataFolder, "buildSrc${separator}src")
    val backupDir get() = File(plugin.dataFolder, "buildSrc${separator}backup")
}