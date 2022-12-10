package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.Header
import org.bukkit.Bukkit
import kotlin.concurrent.thread

class RestServer(val plugin: GamePlugin) {

    val app by lazy { Javalin.create(/*config*/) }

    init {
        thread {
            "/game-stats" get { """{"online": "${Bukkit.getOnlinePlayers().size}"}""" }
            "chat" get { ctx ->
                println(ctx.path())
                ""
            }
            app.start(8080)
            plugin.addDisableEvent { app.stop() }
            println("RestServer Started!")

        }
    }

    infix fun String.get(block: (Context) -> String) {
        app.get(this) { ctx ->
            ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            ctx.json(block(ctx))
        }
    }

}