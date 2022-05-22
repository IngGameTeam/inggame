package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.item.impl.ItemShopMenu
import io.github.inggameteam.item.game.MinigameMenu
import io.github.inggameteam.item.impl.HandyGun
import io.github.inggameteam.item.impl.ShotGun
import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.handle.*
import io.github.inggameteam.minigame.impl.*
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.mongodb.api.MongoDBCPImpl
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.party.PartyCacheSerializer
import io.github.inggameteam.party.PartyItem

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
    ),
) {

    override fun onEnable() {
        super.onEnable()
        MinigameCommand(this)
        ReloadWatchDog(this)
        NoHunger(this, worldName)
        HandleDeath(this)
        ClearEntityUnloadedChunk(this)
        PartyCacheSerializer.deserialize(this)
        HideJoinLeaveMessage(this)
        val mongoDBCP = MongoDBCPImpl(this)
        val user = UserContainer(this, mongoDBCP)
        val purchase = PurchaseContainer(this, mongoDBCP)
        ItemShopMenu(this, user)
        HandyGun(this)
        ShotGun(this)
        MinigameMenu(this)
        PartyItem(this)

    }

    override fun onDisable() {
        super.onDisable()
        PartyCacheSerializer.serialize(this)
    }
}
