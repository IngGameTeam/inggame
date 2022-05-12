package io.github.inggameteam.party

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TestServer {
    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin
    }

    @BeforeTest
    fun setUp() {
        println("setUp")
        server = MockBukkit.mock()
        plugin = MockBukkit.load(TestPlugin::class.java) as TestPlugin
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun partyJoin() {
        println("addPlayer")
        val player = joinPlayer()
        server.onlinePlayers.forEach { it.throwOutMsg() }
    }

    fun joinPlayer(): PlayerMock {
        val addPlayer = server.addPlayer()
        server.pluginManager.callEvent(PlayerJoinEvent(addPlayer, null))
        return addPlayer!!
    }

}




fun PlayerMock.throwOutMsg() {
    while(true) { println("[$name] \n" + nextMessage().apply { if (this === null) return }) }
}