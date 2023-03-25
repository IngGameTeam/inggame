package io.github.inggameteam.inggame.inggame

import io.github.inggameteam.inggame.component.ComponentServiceBean
import io.github.inggameteam.inggame.utils.ClassUtil
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import java.util.UUID.randomUUID
import java.util.logging.Level
import java.util.logging.Logger

abstract class IngGamePluginImp : IngGamePlugin, JavaPlugin() {

    override val console: UUID by lazy { randomUUID() }
    override var allowTask = false
    override val isMockTest: Boolean
        get() = dataFolder.name.lowercase() != name.lowercase()
    private val disableEvent = ArrayList<() -> Unit>()
    override fun addDisableEvent(action: () -> Unit) { disableEvent.add(action) }

    private val saveEvent = ArrayList<() -> Unit>()
    override fun addSaveEvent(action: () -> Unit) { saveEvent.add(action) }

    val ingGame by lazy { IngGameMain() }

    override fun onEnable() {
//        super.onEnable()
        Logger.getLogger("NBTAPI").level = Level.OFF
        Bukkit.getScheduler().cancelTasks(this)
        HandlerList.unregisterAll(this as Plugin)
        initializeGameFile()
        Bukkit.getPluginManager().registerEvents(this, this)
        allowTask = true
        console
        registerModule()
        ComponentServiceBean(this)
        ingGame.app
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
        for (it in disableEvent) it()
        for (it in saveEvent) it()
        if (ingGame.isLoaded()) {
            ingGame.closeApp()
//             Bukkit.getPluginManager().plugins.filter { it != this }.filterIsInstance<IngGamePlugin>().forEach {
//                try { ;{ PluginUtil.unload(it) }.runNow(it) }
//                catch (_: Throwable) {}
//            }
        }
    }

}