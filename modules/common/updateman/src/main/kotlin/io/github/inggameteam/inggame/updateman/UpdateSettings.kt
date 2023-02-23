package io.github.inggameteam.inggame.updateman

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import org.bukkit.Bukkit
import java.io.File
import java.io.File.separator

interface UpdateSettingsWrapper : Wrapper {
    val pluginName: String
    val gitUrl: String
    val branchName: String
    val outputPath: String
    val bashCmd: String
}

interface UpdateSettings : UpdateSettingsWrapper {

    val outputFileName: String get() = outputFile.absoluteFile.name
    val backupFile get() = File(backupDir, outputFileName)
    val destinyFile get() = File("plugins${separator}update", outputFileName)
    val outputFile get() = File(gitDir, outputPath)
    val pluginOrNull get() = Bukkit.getPluginManager().getPlugin(pluginName)
    val plugin get() = pluginOrNull!!
    val gitDir get() = File(plugin.dataFolder, "buildSrc${separator}src")
    val backupDir get() = File(plugin.dataFolder, "buildSrc${separator}backup")

}

class UpdateSettingsImp(wrapper: Wrapper) : UpdateSettings, SimpleWrapper(wrapper) {

    override val pluginName: String by nonNull
    override val gitUrl: String by nonNull
    override val branchName: String by nonNull
    override val outputPath: String by nonNull
    override val bashCmd: String by nonNull
}