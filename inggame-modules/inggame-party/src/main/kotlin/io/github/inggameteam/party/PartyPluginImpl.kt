package io.github.inggameteam.party

import io.github.inggameteam.alert.AlertPluginImpl
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

open class PartyPluginImpl : PartyPlugin, AlertPluginImpl {
    constructor()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File)
            : super(loader, description, dataFolder, file)

    override val partyRegister by lazy { PartyRegister(this) }
    override val partyRequestRegister by lazy { PartyRequestRegister(this) }
    override val partyUI by lazy { PartyUI(this) }
    override fun onEnable() {
        super.onEnable()
        partyRegister
        partyRequestRegister
        partyUI
    }
}