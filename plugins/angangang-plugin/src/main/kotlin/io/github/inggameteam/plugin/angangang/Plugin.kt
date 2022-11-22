package io.github.inggameteam.plugin.angangang

import io.github.inggameteam.challenge.impl.*
import io.github.inggameteam.item.game.*
import io.github.inggameteam.item.impl.*
import io.github.inggameteam.minigame.GamePluginImpl
import io.github.inggameteam.minigame.handle.*
import io.github.inggameteam.minigame.impl.*
import io.github.inggameteam.minigame.ui.MinigameCommand
import io.github.inggameteam.minigame.ui.ModeratePointAmountCommand
import io.github.inggameteam.mongodb.api.MongoDBCPImpl
import io.github.inggameteam.mongodb.impl.*
import io.github.inggameteam.party.PartyCacheSerializer
import io.github.inggameteam.party.PartyItem
import io.github.inggameteam.swear.handle.ChatSwearFilter
import org.bukkit.Bukkit
import org.bukkit.Location

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
        ::Develop,
        ::Tutorial,
        ::JobWars,
        ::Zombies,
    ),
) {

    override fun onEnable() {
        super.onEnable()
        PartyCacheSerializer.deserialize(this)

        val mongoDBCP = MongoDBCPImpl(this)
        val user = UserContainer(this, mongoDBCP)
        val purchase = PurchaseContainer(this, mongoDBCP)
        val challenge = ChallengeContainer(this, mongoDBCP)
        val gameStats = GameStats(this, mongoDBCP)
        val votes = Votes(this, mongoDBCP)
        val chat = Chat(this, mongoDBCP)
        val gameLog = UserLog(this, mongoDBCP)

        listOf(
            ::ADrawIsntBadEither,
            ::AMagicianOfPsychology,
            ::AmazingHardWork,
            ::AnAwkwardVictory,
            ::FirstBlood,
            ::GameLife,
            ::GoJongWonMon,
            ::IDontUseShovels,
            ::IsItAPerson,
            ::LetsHaveFun,
            ::Loser,
            ::Mafia,
            ::NonDestructive,
            ::PentaKill,
            ::TakeThisBoom,
            ::ThePlayer,
            ::TheThiefAndrew,
            ::WaBadGames,
            ::YouAreKiller,
            ::YouKilledIt,
//            ::FirstJoinTutorial,
            ::MadMonsterOfGame,
            ::ThePassingNecklaceInHisHand,
            ::CompleteDefeat,
            ::Ttukbaegi,
            ::SuppressPhysical,
            ).forEach { it(this, challenge) }

        GameLogger(this, gameLog)
        ChatLogger(this, chat)
        NoSaveChunk(this)
        ChatSwearFilter(this)
        SpectateOnJoinParty(this)
        LogGameStats(this, gameStats)
        TutorialBook(this, purchase)
        PoliceHat(this, purchase)
        AnnounceChallengeArchive(this)
        Meteor(this, purchase)
        FireWorks(this, purchase)
        ApplyShopItem(this, purchase)
        RewardWinnerThePoint(this, user)
        MinigameCommand(this)
        ModeratePointAmountCommand(this, user)
        ReloadWatchDog(this)
        NoHunger(this, worldName)
        HandleDeath(this)
        HideJoinLeaveMessage(this)
        ArrowStuckPreventHandler(this)
        DisableCollision(this)
//        AutoUpdater(this)
        RewardVote(this, user, votes)
        NoUnderWaterFall(this)

        ItemShopMenu(this, user, purchase)
        HandyGun(this)
        ShotGun(this)
        MinigameMenu(this)
        MinigameMenu(this, "developing-game-menu")
        PartyItem(this)
        DoubleJump(this)
        Bazooka(this)
        BigBoom(this)
        ShuffleGame(this)
        FlakJacket(this)

        RestServer(this)

        worldName.forEach { worldName ->
            Chunky(Location(Bukkit.getWorld(worldName),
                gameRegister.sectorWidth.toDouble(),
                gameRegister.sectorHeight.toDouble(),
                gameRegister.sectorWidth.toDouble()
            ))
        }
    }

    override fun onDisable() {
        PartyCacheSerializer.serialize(this)
        super.onDisable()
    }
}
