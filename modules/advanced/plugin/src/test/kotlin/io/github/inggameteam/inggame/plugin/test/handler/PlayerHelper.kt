package io.github.inggameteam.inggame.plugin.test.handler

import be.seeseemelk.mockbukkit.entity.PlayerMock
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory
import io.github.inggameteam.inggame.plugin.test.SERVER
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.net.InetAddress
import kotlin.concurrent.thread

fun joinPlayer(name: String): PlayerMock {
    val player = PlayerMockFactory(SERVER).createRandomPlayer()
    val thread = thread {
        SERVER.pluginManager.callEvent(
            AsyncPlayerPreLoginEvent(
                name,
                InetAddress.getLoopbackAddress(),
                player.uniqueId
            )
        )
    }
    while (thread.isAlive) {/**/}
    SERVER.addPlayer(player)
    return player
}

fun receiveMessage(player: PlayerMock) {
    var msg: String? = null
    do {
        msg?.apply { println("${player.name} recieved message=$this")}
        msg = player.nextMessage()
    } while(msg !== null)
}