package io.github.inggameteam.mongodb.impl

import io.github.inggameteam.api.IngGamePlugin
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.mongodb.api.MongoDBCP
import io.github.inggameteam.utils.fastToString
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import kotlin.test.assertNotNull

open class UUIDUser(val uuid: UUID)

abstract class Container<DATA : UUIDUser>(
    final override val plugin: IngGamePlugin,
    val database: String,
    val collection: String,
    val mongo: MongoDBCP,
) : PluginHolder<IngGamePlugin>, Listener, PoolImpl<DATA>(plugin) {

    init { Bukkit.getOnlinePlayers().forEach { pool(it.uniqueId) } }
    init { Bukkit.getPluginManager().registerEvents(this, plugin) }
    val col get() = mongo.client.getDatabase(database).getCollection(collection)

    @Suppress("unused")
    @EventHandler(priority = EventPriority.MONITOR)
    fun onLogin(event: AsyncPlayerPreLoginEvent) {
        val before = System.currentTimeMillis()
        pool.add(pool(event.uniqueId))
        println("${System.currentTimeMillis() - before} ms")
        println(pool.size)
    }

    private fun upsertAndRemove(uuid: UUID) {
        pool.firstOrNull { uuid == it.uuid }?.apply { upsert(this) }
        pool.removeIf { it.uuid == uuid }
    }

    @Suppress("unused")
    @EventHandler
    fun onQuitUploadMongo(event: PlayerQuitEvent) = upsertAndRemove(event.player.uniqueId)

    @Suppress("unused")
    @EventHandler
    fun onKickedUpsert(event: PlayerKickEvent) = upsertAndRemove(event.player.uniqueId)

    operator fun get(key: UUID) = pool.firstOrNull { key == it.uuid }
        ?.apply { assertNotNull(this, "${javaClass.simpleName} does not contain ${key.fastToString()}") }!!
    operator fun get(key: Player) = get(key.uniqueId)

}