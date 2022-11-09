package io.github.inggameteam.minigame.ui

import com.rylinaux.plugman.util.PluginUtil
import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.downloader.download
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.LeftType
import io.github.inggameteam.scheduler.repeat
import io.github.inggameteam.utils.ColorUtil.color
import io.github.inggameteam.world.FixLight
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin

class MinigameCommand(plugin: GamePlugin) : CommandExecutor by MCCommand(plugin as JavaPlugin, {

    commandExecute("lobby", "l") {
        plugin.gameRegister.join(player, plugin.gameRegister.hubName)
    }

    command("mg") {
        thenExecute("join") {
            plugin.gameRegister.join(player, args.subList(1, args.size).joinToString(" "))
        }
        thenExecute("leave") {
            plugin.gameRegister.left(player, LeftType.COMMAND)
        }
        thenExecute("log") {
            source.sendMessage("-------------------------------------")
            plugin.gameRegister.forEach { g ->
                source.sendMessage(
                    "&aname: &6${g.name}".color,
                    "&asector: &6${g.point}".color,
                    "&astatus: &6${g.gameState.name.lowercase()}".color,
                    ("&bjoined: " + g.joined.map { "${it}(${it.tags.joinToString(", ")})" }).color,
                )
            }
            source.sendMessage("-------------------------------------")
        }
        thenExecute("party") {
            source.sendMessage("-------------------------------------")
            plugin.partyRegister.forEach { party ->
                source.sendMessage(
                    ("&ajoined(${party.joined.size}): &6" + party.joined).color,
                )
            }
            source.sendMessage("-------------------------------------")
        }
        then("debug") {
            thenExecute("location") {
                player.location.apply {
                    val width = plugin.gameRegister.sectorWidth
                    val x = x % width
                    val y = y - plugin.gameRegister.sectorHeight
                    val z = z % width
                    player.sendMessage(
                        "&6x: $x ".color,
                        "&6y: $y ".color,
                        "&6z: $z ".color,
                    )
                }
            }
            thenExecute("material") {
                player.inventory.itemInMainHand.type.name.apply { player.sendMessage(this) }
            }
        }
        thenExecute("reload") {
            source.sendMessage("Reloading...")
            val before = System.currentTimeMillis()
            plugin.addDisableEvent { plugin.unloadWorldsOnDisable = false }
            PluginUtil.reload(plugin)
            val after = System.currentTimeMillis()
            source.sendMessage("Reload Done in ${after - before}ms")
        }
        thenExecute("promise-reload") {
            source.sendMessage("Reload promised... while a minutes")
            val before = System.currentTimeMillis()
            ;
            {
                val after = System.currentTimeMillis()
                if (after - before > 1000 * 60) {
                    source.sendMessage("Reload-promise timeout!")
                    false
                } else if (!plugin.playerRegister.any { plugin.gameRegister.getJoinedGame(it.value).name != plugin.gameRegister.hubName }) {
                    PluginUtil.reload(plugin)
                    false
                } else true
            }.repeat(plugin, 1, 1)
        }
        thenExecute("promise-update") {
            source.sendMessage("Update promised... while a minutes")
            val before = System.currentTimeMillis()
            ;
            {
                val after = System.currentTimeMillis()
                if (after - before > 1000 * 60) {
                    source.sendMessage("Update-promise timeout!")
                    false
                } else if (!plugin.playerRegister.any { plugin.gameRegister.getJoinedGame(it.value).name != plugin.gameRegister.hubName }) {
                    download(plugin)
                    PluginUtil.reload(plugin)
                    false
                } else true
            }.repeat(plugin, 1, 1)
        }
        thenExecute("update") {
            download(plugin)
            plugin.addDisableEvent { plugin.unloadWorldsOnDisable = false }
            PluginUtil.reload(plugin)
        }
        thenExecute("fixlight") {
            FixLight().fixLight()
        }
    }

})