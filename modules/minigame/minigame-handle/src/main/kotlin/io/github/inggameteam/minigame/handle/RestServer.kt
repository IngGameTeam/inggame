package io.github.inggameteam.minigame.handle

import io.github.inggameteam.minigame.GamePlugin
import io.javalin.Javalin
import kotlin.concurrent.thread

class RestServer(val plugin: GamePlugin) {

    init {
        thread {
            val app: Javalin = Javalin.create()
                .port(8080)
                .start()
            app.get("/game-stats") { ctx ->
                ctx.json(
                    """
                {"message": "Hello World"}
                """.trimIndent()
                )
            }
        }
    }

}