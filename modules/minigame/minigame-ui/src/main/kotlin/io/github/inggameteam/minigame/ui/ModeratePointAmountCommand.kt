package io.github.inggameteam.minigame.ui

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.mongodb.impl.UserContainer
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class ModeratePointAmountCommand(plugin: GamePlugin, user: UserContainer) : CommandExecutor by MCCommand(plugin as JavaPlugin, {

    command("point") {

        thenExecute("add") { args[1].toLongOrNull()?.apply { user[player].point += this } }
        thenExecute("remove") { args[1].toLongOrNull()?.apply { user[player].point -= this } }
        thenExecute("clear") { user[player].point = 0 }
        thenExecute("set")  { args[1].toLongOrNull()?.apply { user[player].point = this } }

    }

})