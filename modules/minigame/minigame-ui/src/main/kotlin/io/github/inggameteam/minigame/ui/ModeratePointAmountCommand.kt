package io.github.inggameteam.minigame.ui

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.scheduler.async
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class ModeratePointAmountCommand(plugin: GamePlugin, user: UserContainer) : CommandExecutor by MCCommand(plugin as JavaPlugin, {

    command("point") {
        execute {
            if (args.size == 0) {
                val point = user[player].point
                plugin.component.send("MY_PLAYER_POINT", plugin[player], point)
            } else Bukkit.getPlayerExact(args[0])?.let { bukkitPlayer ->
                val point = user[bukkitPlayer].point
                plugin.component.send("OTHER_PLAYER_POINT", plugin[player], plugin[bukkitPlayer], point)
            }
        }
        thenExecute("add") { if (player.isOp) args[1].toLongOrNull()?.apply { user[player].point += this } }
        thenExecute("remove") { if (player.isOp) args[1].toLongOrNull()?.apply { user[player].point -= this } }
        thenExecute("clear") { if (player.isOp) user[player].point = 0 }
        thenExecute("set")  { if (player.isOp) args[1].toLongOrNull()?.apply { user[player].point = this } }
    }

})