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
    val backupFile get() = File(backupDir, outputFile.name)
    val destinyFile get() = File("plugins", outputFile.name)
    val outputFile get() = File(gitDir, outputPath)
    val pluginOrNull get() = Bukkit.getPluginManager().getPlugin(pluginName)
    val plugin get() = pluginOrNull!!
    val gitDir get() = File(plugin.dataFolder, "buildSrc${separator}src")
    val backupDir get() = File(plugin.dataFolder, "buildSrc${separator}backup")
}