package io.github.inggameteam.inggame.plugin

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.view.model.ComponentServiceViewImp
import io.github.inggameteam.inggame.component.view.model.ViewImp
import io.github.inggameteam.inggame.component.view.selector.NameSpaceSelector
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
                    measureTimeMillis {
                        repeat (2000) {
//                            componentService.get(nameSpace, ::GPlayer)
                            source.sendMessage(componentService.find(nameSpace, key, Any::class).toString())
                        }
                    }.run(Any::toString).apply(source::sendMessage)
                }
            }
            then("component") {
                execute {
                    val componentService = app.get<ComponentService>(named(args[1]))
                    if (source !is Player) {
                        measureTimeMillis {
                            println(componentService.getAll().joinToString("\n"))
                        }.apply(::println)
                    } else {
                        NameSpaceSelector(
                            ComponentServiceViewImp(ViewImp(app, this@run, player), componentService)
                        ).open(player)
                    }
                }
            }
        }
    }
}