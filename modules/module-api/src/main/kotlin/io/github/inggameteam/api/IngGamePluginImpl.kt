package io.github.inggameteam.api

import io.github.inggameteam.utils.ClassUtil
import io.github.inggameteam.utils.randomUUID
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class IngGamePluginImpl : IngGamePlugin, JavaPlugin {

    override val console by lazy { randomUUID() }
    override var allowTask = false
    val disableEvent = ArrayList<() -> Unit>()
    override fun addDisableEvent(action: () -> Unit) { disableEvent.add(action) }

    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override fun onEnable() {
//        super.onEnable()
        Bukkit.getScheduler().cancelTasks(this)
        HandlerList.unregisterAll(this as Plugin)
        if (dataFolder.listFiles()?.isNotEmpty() != true) {
            val extensions = arrayOf(".yml", ".schem", ".md", ".txt")
            ClassUtil.getDirectory(this.javaClass)
                .filter { extensions.any { ext -> it.endsWith(ext) } && it != "plugin.yml" }
                .filter { !File(dataFolder, it).exists() }
                .forEach { saveResource(it, false) }
        }
        Bukkit.getPluginManager().registerEvents(this, this)
        allowTask = true
        console
    }

    override fun onDisable() {
//        super.onDisable()
        allowTask = false
        for (it in disableEvent) it()
    }

}