package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.mongodb.api.MongoDBCP
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import java.util.*

open class UUIDUser(val uuid: UUID)

abstract class Container<DATA : UUIDUser>(
    override val plugin: Plugin,
    val database: String,
    val collection: String,
    val mongo: MongoDBCP,
) : PluginHolder<Plugin>, Listener {

    private val container = HashSet<DATA>()

    init { Bukkit.getOnlinePlayers().forEach { pool(it.uniqueId) } }
    init { Bukkit.getPluginManager().registerEvents(this, plugin) }
    val col get() = mongo.client.getDatabase("user").getCollection("user")

    abstract fun pool(uuid: UUID): DATA

    abstract fun upsert(data: DATA)

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onLogin(event: AsyncPlayerPreLoginEvent) {
        container.add(pool(event.uniqueId))
    }

    @Suppress("unused")
    @EventHandler
    fun onQuitUploadMongo(event: PlayerQuitEvent) {
        val uniqueId = event.player.uniqueId
        container.firstOrNull { uniqueId == it.uuid }?.apply { upsert(this) }
        container.removeIf { it.uuid == uniqueId }
    }

}