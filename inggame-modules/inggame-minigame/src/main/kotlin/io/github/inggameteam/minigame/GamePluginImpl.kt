package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPluginImpl
import io.github.inggameteam.utils.IntVector
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class GamePluginImpl : GamePlugin, PartyPluginImpl {
    lateinit var hubName: String
    lateinit var worldName: String
    lateinit var worldSize: IntVector
    lateinit var init: Array<(GamePlugin, IntVector) -> Game>

    constructor(hubName: String,
                worldName: String,
                worldSize: IntVector,
                init: Array<(GamePlugin, IntVector) -> Game>) {
        this.hubName = hubName
        this.worldName = worldName
        this.worldSize = worldSize
        this.init = init
    }

    constructor(hubName: String, worldName: String, worldSize: IntVector,
                init: Array<(GamePlugin, IntVector) -> Game>,
                loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file) {
        this.hubName = hubName
        this.worldName = worldName
        this.worldSize = worldSize
        this.init = init
    }

    override val gameSupplierRegister by lazy { GameSupplierRegister(this, *init) }
    override val gameRegister by lazy { GameRegister(this, hubName, worldName, worldSize) }
    override fun onEnable() {
        super.onEnable()
        gameSupplierRegister
        gameRegister
    }
}