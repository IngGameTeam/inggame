package io.github.inggameteam.inggame.utils

import io.github.inggameteam.inggame.utils.event.IngGamePluginDisableEvent
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.util.*
import java.util.UUID.randomUUID

open class IngGamePluginImp : IngGamePlugin, JavaPlugin {

    override val console: UUID by lazy { randomUUID() }
    override var allowTask = false
    override val isMockTest: Boolean
        get() = dataFolder.name.lowercase() != name.lowercase()
    private val disableEvent = ArrayList<() -> Unit>()
    override fun addDisableEvent(action: () -> Unit) { disableEvent.add(action) }

    private val saveEvent = ArrayList<() -> Unit>()
    override fun addSaveEvent(action: () -> Unit) { saveEvent.add(action) }

    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override fun onEnable() {
//        super.onEnable()
        Bukkit.getScheduler().cancelTasks(this)
        HandlerList.unregisterAll(this as Plugin)
        initializeGameFile()
        Bukkit.getPluginManager().registerEvents(this, this)
        allowTask = true
        console
    }

     fun initializeGameFile(force: Boolean = false) {
         if (isMockTest) dataFolder.listFiles()?.filter(File::isFile)?.forEach(File::deleteRecursively)
        if (dataFolder.listFiles()?.isNotEmpty() != true || isMockTest) {
            val excludes = listOf("plugin.yml", "MANIFEST.MF")
            ClassUtil.getDirectory(this.javaClass)
                .filter { !it.endsWith(".class") && !excludes.contains(it) }
                .filter { force || !File(dataFolder, it).exists() }
                .forEach { saveResource(it, false) }
        }

    }

    override fun onDisable() {
//        super.onDisable()
        allowTask = false
        server.pluginManager.callEvent(IngGamePluginDisableEvent(this))
        for (it in disableEvent) it()
        for (it in saveEvent) it()
    }

}