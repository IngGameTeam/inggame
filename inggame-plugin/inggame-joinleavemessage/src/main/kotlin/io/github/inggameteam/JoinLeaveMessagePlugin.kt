package io.github.inggameteam

import io.github.inggameteam.alert.AlertComponent
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameRegister
import io.github.inggameteam.minigame.GameSupplierRegister
import io.github.inggameteam.party.PartyRegister
import io.github.inggameteam.party.PartyRequestRegister
import io.github.inggameteam.party.PartyUI
import io.github.inggameteam.player.GPlayerRegister
import io.github.inggameteam.utils.randomUUID
import org.bukkit.plugin.java.JavaPlugin

class JoinLeaveMessagePlugin : JavaPlugin(), GamePlugin {
    override val gameRegister = GameRegister(this, hubName = "hub")
    override val gameSupplierRegister = GameSupplierRegister(*arrayOf(

    ))
    override val partyRegister = PartyRegister(this)
    override val partyRequestRegister = PartyRequestRegister(this)
    override val partyUI = PartyUI(this)
    override val playerRegister = GPlayerRegister(this)
    override val alertComponent = AlertComponent(dataFolder)
    override val console = randomUUID()
}

