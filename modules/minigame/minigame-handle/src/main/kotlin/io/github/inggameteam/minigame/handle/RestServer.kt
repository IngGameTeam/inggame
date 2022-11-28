package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.javalin.Javalin
import io.javalin.http.Header
import org.bukkit.Bukkit
import kotlin.concurrent.thread

class RestServer(val plugin: GamePlugin) {

    init {
        thread {
            val app = Javalin.create(/*config*/)
                .get("/game-stats") { ctx ->
                    ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    ctx.json(
                    """
                {
                    "message": "Hello World",
                    "online": ${Bukkit.getOnlinePlayers()},
                }
                """.trimIndent()
                )
            }
                .start(8080)
            println("RestServer Started!")
            plugin.addDisableEvent {
                app.stop()
            }

        }
    }

}