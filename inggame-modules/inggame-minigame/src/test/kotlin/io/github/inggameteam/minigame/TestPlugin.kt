package io.github.inggameteam.minigame

import io.github.inggameteam.alert.Component
import io.github.inggameteam.party.PartyRegister
import io.github.inggameteam.party.PartyRequestRegister
import io.github.inggameteam.party.PartyUI
import io.github.inggameteam.player.GPlayerRegister
import io.github.inggameteam.utils.IntVector
import io.github.inggameteam.utils.randomUUID
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.util.*

class TestPlugin : JavaPlugin, GamePlugin {
    override val console = randomUUID()
    override val component by lazy { Component(dataFolder) }
    override val playerRegister by lazy { GPlayerRegister(this) }
    override val partyRegister by lazy { PartyRegister(this) }
    override val partyRequestRegister by lazy { PartyRequestRegister(this) }
    override val partyUI by lazy { PartyUI(this) }
    override val gameSupplierRegister by lazy { GameSupplierRegister() }
    override val gameRegister by lazy { GameRegister(this, "hub", "customized_world", IntVector(300, 128)) }

    protected constructor()
    protected constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File,
    ) :super(loader, description, dataFolder, file)


}