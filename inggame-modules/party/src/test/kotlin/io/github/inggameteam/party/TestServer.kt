package io.github.inggameteam.party

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
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
        val player = server.addPlayer()
        player.performCommand("p create")
        player.performCommand("p ${server.addPlayer().name}")
        server.onlinePlayers.forEach { it.throwOutMsg() }
    }

}

fun PlayerMock.throwOutMsg() {
    while(true) { println("[$name] \n" + nextMessage().apply { if (this === null) return }) }
}