package io.github.inggameteam.plugin.cheesesand

import io.github.inggameteam.item.game.MinigameMenu
import io.github.inggameteam.item.impl.*
import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.handle.*
import io.github.inggameteam.minigame.impl.*
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.party.PartyCacheSerializer
import io.github.inggameteam.party.PartyItem
import io.github.inggameteam.plugin.cheesesand.game.Hub

@Suppress("unused")
class Plugin : GamePluginImpl(
    hubName = "hub",
    width = 300, height = 128,
    init = arrayOf(
        ::Hub,
        ::TNTTag,
        ::TNTRun,
        ::RandomWeaponWar,
        ::BlockHideAndSeek,
        ::TeamWars,
        ::AvoidAnvil,
        ::BedWars,
        ::BoatRider,
        ::BuildBattle,
        ::CaptureTheWool,
        ::FallJump,
        ::HideAndSeek,
        ::HunchGame,
        ::Quiz,
        ::Soccer,
        ::Spleef,
        ::TakeTheCart,
        ::PushGame,
        ::PigRider,
        ::ColorMatch,
        ::ZombieSurvival,
    )
) {

    override fun onEnable() {
        super.onEnable()
        PartyCacheSerializer.deserialize(this)
        /*************************************/
        MinigameCommand(this)
        MinigameMenu(this)
        HandleDeath(this)
        ClearEntityUnloadedChunk(this)
        ArrowStuckPreventHandler(this)
        ReloadWatchDog(this)
        PartyItem(this)

        HandyGun(this)
        ShotGun(this)
        DoubleJump(this)
        Bazooka(this)
        BigBoom(this)

        /*************************************/

    }

    override fun onDisable() {
        super.onDisable()
        PartyCacheSerializer.serialize(this)
    }
}