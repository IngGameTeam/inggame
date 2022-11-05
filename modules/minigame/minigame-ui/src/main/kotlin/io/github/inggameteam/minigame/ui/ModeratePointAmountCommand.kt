package io.github.inggameteam.minigame.ui

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.UserContainer
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ModeratePointAmountCommand(plugin: GamePlugin, user: UserContainer) : CommandExecutor by MCCommand(plugin as JavaPlugin, {

    command("point") {
        execute {
            if (source is Player) {
                source.sendMessage("Only console can execute this command")
                return@execute
            }
            val player = plugin[Bukkit.getPlayer(args[0])!!]
            if (args.isEmpty()) {
                val point = user[player].point
                plugin.component.send("MY_PLAYER_POINT", plugin[player], point)
            } else Bukkit.getPlayerExact(args[0])?.let { bukkitPlayer ->
                val point = user[bukkitPlayer].point
                plugin.component.send("OTHER_PLAYER_POINT", plugin[player], plugin[bukkitPlayer], point)
            }
        }
        thenExecute("add") {
            val lastArgs = args[1].split(" ")
            if (source is Player) {
                source.sendMessage("Only console can execute this command")
                return@thenExecute
            }
            println(args)
            val player = plugin[Bukkit.getPlayer(lastArgs[0])!!]
            if (player.isOp) lastArgs[1].toLongOrNull()?.apply { user[player].point += this } }
        thenExecute("remove") {
            val lastArgs = args[1].split(" ")
            if (source is Player) {
                source.sendMessage("Only console can execute this command")
                return@thenExecute
            }
            val player = plugin[Bukkit.getPlayer(lastArgs[0])!!]
            if (player.isOp) lastArgs[1].toLongOrNull()?.apply { user[player].point -= this } }
        thenExecute("clear") {
            val lastArgs = args[1].split(" ")
            if (source is Player) {
                source.sendMessage("Only console can execute this command")
                return@thenExecute
            }
            val player = plugin[Bukkit.getPlayer(lastArgs[0])!!]
            if (player.isOp) user[player].point = 0 }
        thenExecute("set") {
            val lastArgs = args[1].split(" ")
            if (source is Player) {
                source.sendMessage("Only console can execute this command")
                return@thenExecute
            }
            val player = plugin[Bukkit.getPlayer(lastArgs[0])!!]
             if (player.isOp) lastArgs[1].toLongOrNull()?.apply { user[player].point = this }
        }
    }

})