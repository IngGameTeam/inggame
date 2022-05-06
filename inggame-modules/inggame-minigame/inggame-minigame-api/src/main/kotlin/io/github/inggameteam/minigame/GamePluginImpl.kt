package io.github.inggameteam.minigame

import io.github.inggameteam.party.PartyPluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class GamePluginImpl : GamePlugin, PartyPluginImpl {
    lateinit var hubName: String
    lateinit var worldName: String
    var width: Int = 0
    var height: Int = 0
    lateinit var init: Array<(GamePlugin, Sector) -> Game>

    constructor()
    constructor(hubName: String,
                worldName: String,
                width: Int, height: Int,
                init: Array<(GamePlugin, Sector) -> Game>) {
        this.hubName = hubName
        this.worldName = worldName
        this.width = width
        this.height = height
        this.init = init
    }

    constructor(hubName: String,
                worldName: String,
                width: Int, height: Int,
                init: Array<(GamePlugin, Sector) -> Game>,
                loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file) {
        this.hubName = hubName
        this.worldName = worldName
        this.width = width
        this.height = height
        this.init = init
    }

    override val gameSupplierRegister by lazy { GameSupplierRegister(this, *init) }
    override val gameRegister by lazy { GameRegister(this, hubName, worldName, width, height) }
    override fun onEnable() {
        super.onEnable()
        gameSupplierRegister
        gameRegister
    }
}