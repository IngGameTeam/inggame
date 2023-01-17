package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.editor.model.NameSpaceSelectorViewImp
import io.github.inggameteam.inggame.component.view.editor.nsSelector
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.fastUUID
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.system.measureTimeMillis

fun debugCommand(plugin: IngGamePlugin, app: Koin) = plugin.run {
    MCCommand(this as JavaPlugin) {
        command("ing") {
            then("get") {
                execute {
                    val split = args[1].split(" ")
                    val componentService = app.get<ComponentService>(named(split[0]))
                    val nameSpace = split[1].run { try { fastUUID() } catch (_: Throwable) { this } }
                    val key = split[2]
                    source.sendMessage(componentService.get(nameSpace, key, Any::class).toString())
                }
            }
            then("component") {
                execute {
                    val componentService = app.get<ComponentService>(named(args[1]))
                    if (source !is Player) {
                        measureTimeMillis {
                            println(componentService)
                        }.apply(::println)
                    } else {
                        nsSelector(NameSpaceSelectorViewImp(componentService, app, this@run))
                            .openInventory(player)
                    }
                }
            }
        }
    }
}