package io.github.inggameteam.inggame.inggame

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.EmptyComponentService
import io.github.inggameteam.inggame.component.componentservice.MultiParentsComponentService
import io.github.inggameteam.inggame.component.view.controller.NameSpaceSelector
import io.github.inggameteam.inggame.component.view.entity.ComponentServiceViewImp
import io.github.inggameteam.inggame.component.view.entity.ViewImp
import io.github.inggameteam.inggame.plugman.util.PluginUtil
import io.github.inggameteam.inggame.utils.*
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.Koin
import org.koin.core.qualifier.named
import kotlin.system.measureTimeMillis

@Helper
class IngGameCommand(plugin: IngGamePlugin) {
    init {
        { debugCommand(plugin, (plugin as IngGamePluginImp).ingGame.app) }.runNow(plugin)
    }
    private fun debugCommand(plugin: IngGamePlugin, app: Koin) = plugin.run {
        MCCommand(this as JavaPlugin) {
            command("ing") {
                thenExecute("reload") {
                    if (!source.isOp) return@thenExecute
                    source.sendMessage("Reloading...")
                    val ms = measureTimeMillis { PluginUtil.reload(plugin) }
                    source.sendMessage("Reloaded in ${ms}ms")
                }
                thenExecute("save") {
                    if (!source.isOp) return@thenExecute
                    source.sendMessage("Saving...")
                    val ms = measureTimeMillis { (plugin as IngGamePluginImp).save() }
                    source.sendMessage("Saved in ${ms}ms")
                }
                then("what-type") {
                    tab { app.getAll<ComponentService>().map { it.name } }
                    execute {
                        if (!source.isOp) return@execute
                        source.sendMessage(app.get<ComponentService>(named(args[1])).javaClass.simpleName)
                    }
                }
                then("replace") {
                    tab { app.getAll<ComponentService>().map { it.name } }
                    execute {
                        if (!source.isOp) return@execute
                        val split = args[1].split(" ")
                        val componentService = app.get<ComponentService>(named(split[0]))
                        val replaceOld = split[1]
                        val replaceNew = split[2]
                        componentService.getAll().forEach { ns ->
                            val parents = ns.parents
                            if (parents.remove(replaceOld)) {
                                parents.add(replaceNew)
                            }
                        }
                        source.sendMessage("All $replaceOld in ${componentService.name}'s parents replaced to $replaceNew")
                    }
                }
                then("remove") {
                    tab { app.getAll<ComponentService>().map { it.name } }
                    execute {
                        if (!source.isOp) return@execute
                        val split = args[1].split(" ")
                        val componentService = app.get<ComponentService>(named(split[0]))
                        val replaceOld = split[1]
                        val replaceNew = split[2]
                        componentService.getAll().forEach { ns ->
                            val parents = ns.parents
                            parents.remove(replaceOld)
                        }
                        source.sendMessage("All $replaceOld in ${componentService.name}'s parents replaced to $replaceNew")
                    }
                }
                then("unload") {
                    execute {
                        if (!source.isOp) return@execute
                        source.sendMessage("Unloading...")
                        PluginUtil.unload(this@run)
                        source.sendMessage("Unload done!")
                    }
                }
                then("performance-test") {
                    tab { app.getAll<ComponentService>().map { it.name } }
                    execute {
                        if (!source.isOp) return@execute
                        val split = args[1].split(" ")
                        val componentService = app.get<ComponentService>(named(split[0]))
                        val nameSpace = split[1].run {
                            try {
                                fastUUID()
                            } catch (_: Throwable) {
                                this
                            }
                        }
                        val key = split[2]
                        measureTimeMillis {
                            repeat(3000) {
                                componentService.find(nameSpace, key)
                            }
                        }.run(Any::toString).apply(source::sendMessage)
                    }
                }
                then("get") {
                    tab { app.getAll<ComponentService>().map { it.name } }
                    execute {
                        if (!source.isOp) return@execute
                        val split = args[1].split(" ")
                        val componentService = app.get<ComponentService>(named(split[0]))
                        val nameSpace = split[1].run {
                            try {
                                fastUUID()
                            } catch (_: Throwable) {
                                this
                            }
                        }
                        val key = split[2]
                        source.sendMessage(componentService.find(nameSpace, key).toString())
                    }
                }
                thenExecute("measure-time") {
                    if (!source.isOp) return@thenExecute
                    val time = measureTimeMillis { player.performCommand(args[1]) }
                    player.sendMessage("${time}ms")
                }
                thenExecute("debug") {
                    if (!source.isOp) return@thenExecute
                    val newDebug = !Debug.isDebug
                    if (newDebug) source.sendMessage("Now, Debug mode is ON")
                    else source.sendMessage("Now, Debug mode is OFF")
                    Debug.isDebug = newDebug
                }
                then("component") {
                    fun getComponentServices() = app.getAll<ComponentService>()
                        .filterNot { it is MultiParentsComponentService || it is EmptyComponentService }
                        .map { it.name }
                    tab { getComponentServices() }
                    execute {
                        if (!source.isOp) return@execute
                        if (!getComponentServices().contains(args[1])) {
                            source.sendMessage("Component Not Found")
                            return@execute
                        }
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
}