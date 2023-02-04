package io.github.inggameteam.inggame.minigame.base

import io.github.inggameteam.inggame.component.Handler
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.GamePlayerService
import io.github.inggameteam.inggame.utils.HandleListener
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.randomUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

class PrintOnMove(
    private val gamePlayerService: GamePlayerService,
    plugin: IngGamePlugin
) : HandleListener(plugin), Handler {

    @Suppress("unused")
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = gamePlayerService[event.player.uniqueId, ::GPlayer]
        val map = mapOf(
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
            randomUUID() to randomUUID(),
        ).run(::ConcurrentHashMap)
        measureTimeMillis{
            val key = randomUUID()
            repeat (100){
                repeat(10000) { map.getOrDefault(key, null) }
//                if (isHandler(player)) {
//                    player[::GameAlertImp].GAME_JOIN.send(player)
//                    event.player.sendMessage("${randomUUID()}")
//                }
            }
        }.apply { println(this) }
    }

}