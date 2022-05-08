package io.github.inggameteam.minigame.ui

import io.github.brucefreedy.mccommand.MCCommand
import io.github.brucefreedy.mccommand.player
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.utils.ColorUtil.color
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class MinigameCommand(plugin: GamePlugin) : CommandExecutor by MCCommand(plugin as JavaPlugin, {

    command("mg") {
        thenExecute("join") {
            plugin.gameRegister.join(player, args.subList(1, args.size).joinToString(" "))
        }
        thenExecute("leave") {
            plugin.gameRegister.left(player, LeftType.COMMAND)
        }
        thenExecute("log") {
            player.sendMessage("-------------------------------------")
            plugin.gameRegister.forEach { g ->
                player.sendMessage(
                    ("&aname: &6" + g.name + "&7 / &asector: &6" + g.point + "&7 / &astatus: &6" + g.gameState.name).color,
                    ("&bjoined: " + g.joined.map { "${it}(${it.tags.joinToString(", ")})" }).color,
                )
            }
            player.sendMessage("-------------------------------------")
        }
        thenExecute("party") {
            player.sendMessage("-------------------------------------")
            plugin.partyRegister.forEach { party ->
                player.sendMessage(
                    ("&ajoined(${party.joined.size}): &6" + party.joined).color,
                )
            }
            player.sendMessage("-------------------------------------")
        }
    }

})