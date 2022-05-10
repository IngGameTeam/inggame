package io.github.inggameteam.minigame.ui

import io.github.brucefreedy.mccommand.MCCommand
import io.github.brucefreedy.mccommand.player
import io.github.inggameteam.alert.Alert
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.api.PluginHolder
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

data class DuelRequest(val sender: GPlayer, val receiver: GPlayer)
class Duel(override val plugin: GamePlugin) : HandleListener(plugin), PluginHolder<GamePlugin> {

    val requests = HashSet<DuelRequest>()

    @Deprecated("EventHandler")
    @EventHandler
    fun onLeftServer(event: PlayerQuitEvent) {
        val player = plugin[event.player]
        requests.removeIf { it.sender == player || it.receiver == player }
    }

    init {
        MCCommand(plugin as JavaPlugin) {
            command("duel") {
                tab { plugin.playerRegister.values.map { it.name } }
                execute {
                    val targetPlayer = Bukkit.getPlayerExact(args[0])?.run { plugin[this] }
                    val gPlayer = plugin[player]
                    if (targetPlayer === null) {
                        plugin.component.send(Alert.NO_PLAYER_EXIST, gPlayer)
                        return@execute
                    }
                    val request = DuelRequest(gPlayer, targetPlayer)
                    requests.add(request)
                    plugin.component.send("DUEL_REQUESTED", gPlayer, targetPlayer)
                    plugin.component.send("DUEL_REQUEST", targetPlayer, gPlayer, request.hashCode())
                }
                thenExecute("accept") {
                    val requestedCode = args[1].toIntOrNull()
                    val gPlayer = plugin[player]
                    val req = requests.firstOrNull { it.hashCode() == requestedCode }
                    if (requestedCode === null || req === null) {
                        plugin.component.send("INVALID_DUEL_REQUEST", gPlayer)
                        return@thenExecute
                    }
                    requests.remove(req)
                    plugin.gameRegister.createGame("duel").apply {
                        joinGame(req.sender)
                        joinGame(req.receiver)
                    }
                }
            }
        }
    }


}