package io.github.inggameteam.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class GPlayerRegister(playerPlugin: PlayerPlugin) : HashMap<UUID, GPlayer>(), Listener {

    init {
        Bukkit.getOnlinePlayers().map(Player::getUniqueId).forEach { put(it, GPlayer(it)) }
        Bukkit.getPluginManager().registerEvents(this, playerPlugin)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private fun onJoin(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        this[uuid] = GPlayer(uuid)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private fun onQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        this.remove(uuid)
    }

    operator fun get(player: Player) = this[player.uniqueId]
    override operator fun get(key: UUID) = super.get(key)!!

}