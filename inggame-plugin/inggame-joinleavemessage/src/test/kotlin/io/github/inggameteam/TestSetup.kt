package io.github.inggameteam

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import io.github.inggameteam.joinleavemessage.JoinLeaveMessagePlugin
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class TestSetup {
    lateinit var server: ServerMock
    lateinit var plugin: JoinLeaveMessagePlugin


    @kotlin.test.Test
    fun a() {
        server.broadcast(TextComponent("hello"))
        server.addPlayer("fucker").apply {
            chat("hello")
        }
        println("asdfsdqasdfafsdasafsdafsdfasd")
        server.pluginManager.callEvent(PlayerJoinEvent(server.getPlayer(0), "asdf"))
    }

    @BeforeTest
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(JoinLeaveMessagePlugin::class.java)
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }
}