package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.minigame.GameAlert.GAME_DRAW_NO_WINNER
import io.github.inggameteam.minigame.GameAlert.SINGLE_WINNER
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerSpawnEvent
import io.github.inggameteam.minigame.event.GPlayerWinEvent
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.ColorUtil.color
import io.github.inggameteam.utils.ItemUtil
import io.github.inggameteam.world.FaweImpl
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.boss.BarColor
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.concurrent.thread

class BuildBattle(plugin: GamePlugin) : Game, CompetitionImpl(plugin),
    BarGame, InteractingBan, SpawnPlayer, Respawn, SimpleGame {
    override val name get() = "build-battle"
    override val bar by lazy { GBar(plugin) }
    override val recommendedSpawnDelay get() = -1L
    override val noInteracts = listOf(
        Material.BARRIER,
        Material.PISTON,
        Material.STICKY_PISTON,
        Material.TNT,
        Material.TNT_MINECART,
        Material.ENDER_CHEST,
        Material.RESPAWN_ANCHOR,
    )
    val doneTime get() = comp.doubleOrNull("done-time")?: (20.0 * 100.0)
    val voteTime get() = comp.doubleOrNull("vote-time")?: 100.0
    val areaSize get() = comp.doubleOrNull("area-size")?: 20.0
    var time = doneTime
    var isDone = false
    val vote = HashMap<UUID, Int>()

    val voteTopic = HashMap<String, Int>()
    val exampleTopic = ArrayList<String>()
    lateinit var current: UUID
    lateinit var decidedTopic: String

    @Suppress("unused")
    @EventHandler
    fun notAllowedBlockBreak(event: BlockBreakEvent) {
        if (!isJoined(plugin[event.player])) return
        if (gameState !== GameState.PLAY) return
        if (isDone) return
        val location = playerData[plugin[event.player]]!![PLAYER_AREA] as Location
        val aresSize = areaSize/2 - 2
        if (!event.block.location.toVector().isInAABB(
                location.clone().subtract(aresSize, aresSize, aresSize).toVector(),
                location.clone().add(aresSize, aresSize, aresSize).toVector(),
            )
        ) {
            event.isCancelled = true
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        if (isInSector(event.location)) {
            event.isCancelled = true
        }
    }


    @Suppress("unused")
    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        val player = event.player
        if (isJoined(player)) {
            if (event.block.y <= comp.int("floor-height")) {
                event.isCancelled = true
            }
        }
    }

    override fun inventorySpawn(player: GPlayer, spawn: String): Inventory? {
        return super<SimpleGame>.inventorySpawn(player,
            if (::current.isInitialized && current == player.uniqueId) "CANNOT_VOTE_MYSELF"
            else if (isDone) "VOTE"
            else spawn
        )
    }

    override fun tpSpawn(player: GPlayer, spawn: String): Location?  {
        if (!player.hasTag(PTag.PLAY)) return super<SimpleGame>.tpSpawn(player, spawn)
        if (!isDone) return null
        return (playerData[joined.first { current == it.uniqueId }]!![PLAYER_AREA] as Location).apply {
            player.teleport(this)
        }
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onBeginBuildBattle(event: GameBeginEvent) {
        if (event.game !== this) return
        val location = getLocation(GameState.PLAY.toString())
        var isX = false
        var i = 0.0  //이동 카운트
        var n = 0.0  //이동 길이
        val m = 12  //겹 수
        var count = 0
        val playPlayers = joined.hasTags(PTag.PLAY)
        while (n <= m) {
            val finalLocation = location.clone()
            if (playPlayers.size <= count) break
            playerData[playPlayers[count]]!![PLAYER_AREA] = finalLocation
            thread { FaweImpl(plugin).paste(finalLocation, getSchematicFile("area", name)) }
            count++
            if (i <= n) i++
            else {
                i = 1.0
                isX = !isX
                if (isX) n++
            }
            val adder = if (n % 2 == 1.0) areaSize else -areaSize
            if (isX) location.add(adder, 0.0, 0.0)
            else location.add(0.0, 0.0, adder)
        }
        joined.forEach { spawn(it) }
        voteTopics()
    }

    private fun beginGameTimer() {
        gameTask = ITask.repeat(plugin, 1, 1, {
            if (isDone) {
                if (time <= 0) {
                    time = voteTime
                    nextSpawn()
                    if (joined.any { it.uniqueId == current }) bar.update("${plugin[current]}")
                } else {
                    time--
                    bar.update(progress = time / voteTime, color = BarColor.GREEN)
                }
            }
            else {
                if (time <= 0) {
                    isDone = true
                    time = voteTime
                    nextSpawn()
                    if (joined.any { it.uniqueId == current }) bar.update("${plugin[current]}")

                } else {
                    time--
                    bar.update(
                        alert = { comp.string("left-time-title", it.lang(plugin)).format(decidedTopic) },
                        progress = time / doneTime, color = BarColor.GREEN
                    )
                }
            }
        })
    }

    private fun nextSpawn() {
        if (gameState === GameState.STOP) return
        val newCurrent = joined.hasTags(PTag.PLAY)
            .filter { !vote.keys.contains(it.uniqueId) }.randomOrNull()
        if (newCurrent == null) {
            calcWinner()
            gameTask = {
                if (gameState !== GameState.STOP) stop(false)
            }.delay(plugin, 20 * 4)
            return
        }
        current = newCurrent.uniqueId
        vote[current] = 0
        joined.forEach { spawn(it) }

    }

    override fun calcWinner() {
        if (gameState === GameState.STOP) return
        val firstWin = vote
            .filter { val uuid = it.key; joined.hasTags(PTag.PLAY).any { p -> uuid == p.uniqueId } }
            .keys.asSequence()
            .map { CompareableVote(it, vote[it]!!) }.sorted().toList().lastOrNull()?.data
        if (firstWin === null) return
        val winners = GPlayerList(vote.filterValues { it == firstWin }.keys.map { plugin[it] })
        if (winners.size == joined.hasTags(PTag.PLAY).size) {
            joined.forEach { comp.send(GAME_DRAW_NO_WINNER, it, displayName(it)) }
            return
        }
        joined.forEach { comp.send(SINGLE_WINNER, it, winners, displayName(it)) }
//        winners.forEach{ Context.rewardPoint(it.player, rewardPoint())}
        Bukkit.getPluginManager().callEvent(GPlayerWinEvent(this, winners))
        isDone = true
        current = winners.random().uniqueId
        joined.forEach { spawn(it) }
        gameState = GameState.PLAY
    }

    @Suppress("unused")
    @EventHandler
    fun spawn(event: GPlayerSpawnEvent) {
        val player = event.player
        if (!isJoined(player)) return
        if (!player.hasTag(PTag.PLAY)) return
        if (gameState === GameState.WAIT) {
            return
        } else if (isDone) {
            val location =
                playerData[joined.first { current == it.uniqueId }]!![PLAYER_AREA] as Location
            player.teleport(location)
            if (current == player.uniqueId) {
                player.inventory.clear()
            } else {
                player.inventory.contents = comp.inventory("VOTE", player.lang(plugin)).contents
            }
            player.inventory.heldItemSlot = 7
        } else {
            player.teleport(playerData[player]!![PLAYER_AREA] as Location)
            player.gameMode = GameMode.CREATIVE
            player.isFlying = true
            player.inventory.clear()
        }
    }

    @Suppress("unused")
    @EventHandler
    fun breakBlock(event: BlockBreakEvent) {
        if (isJoined(event.player).not()) return
        if (isDone) event.isCancelled = true
    }

    @Suppress("unused")
    @EventHandler
    fun placeBlock(event: BlockPlaceEvent) {
        if (isJoined(event.player).not()) return
        if (isDone) event.isCancelled = true
    }

    @Suppress("unused")
    @EventHandler
    fun vote(event: PlayerInteractEvent) = clickItem(event.player, event.item, event)

    @Suppress("unused")
    @EventHandler
    fun vote(event: PlayerInteractEntityEvent) = clickItem(event.player, event.player.inventory.getItem(event.hand), event)

    fun clickItem(player: Player, item: ItemStack?, event: Cancellable) {
        if (item === null) return
        if (!isJoined(player)) return
        if (isDone) {
            event.isCancelled = true
            val map = comp.stringList("votes", player.lang(plugin)).map { Pair(it, comp.item(it, player.lang(plugin))) }

            val score = map.firstOrNull { it.second.isSimilar(item) }?.first?: return
            vote[current] = vote.getOrDefault(current, 0) + score.toInt()
            player.inventory.contents = comp.inventory("VOTED", player.lang(plugin)).contents
        }
    }
    val TOPIC_VOTE_TIME get() = comp.doubleOrNull("topic-vote-time")?.toLong() ?: (20 * 10L)
    companion object {
        const val PLAYER_AREA = "playerArea"
        const val TOPIC_VOTE = "topicVote"
        const val IS_VOTING_TOPIC = "isVotingTopic"
        const val TOPIC_AMOUNT = 3
    }

    class CompareableVote(val uuid: UUID, val data: Int) : Comparable<CompareableVote> {
        override fun compareTo(other: CompareableVote) = data.compareTo(other.data)
    }

    class ComparableTopicVote(val name: String, val amount: Int) : Comparable<ComparableTopicVote> {
        override fun compareTo(other: ComparableTopicVote) = amount.compareTo(other.amount)
    }

    fun voteTopics() {
        val topics = ArrayList(comp.stringList("topic", plugin.defaultLanguage))
        repeat(TOPIC_AMOUNT) {
            topics.random().apply {
                topics.remove(this)
                exampleTopic.add(this)
                voteTopic[this] = 0
            }
        }
        joined.forEach(::openMenu)
        gameTask = {
            joined.forEach { playerData[it]!!.remove(IS_VOTING_TOPIC) }
            joined.forEach { it.closeInventory() }
            decidedTopic = voteTopic.keys
                .map { ComparableTopicVote(it, voteTopic[it]!!) }.sorted().toList().lastOrNull()?.name?: exampleTopic.random()
            bar.update(title = decidedTopic)
            comp.send("TOPIC", joined, decidedTopic)
            beginGameTimer()
        }.delay(plugin, TOPIC_VOTE_TIME)
    }

    private fun openMenu(player: GPlayer) {
        player.openFrame(InvFX.frame(TOPIC_AMOUNT, Component.text(comp.string("vote-menu-title", player.lang(plugin)))) {
            onClose { if (playerData[player]!![IS_VOTING_TOPIC] != null) addTask({ openMenu(player) }.delay(plugin, 1)) }
            list(0, 0, 9, TOPIC_AMOUNT, true, {
                val list = ArrayList<String>()
                for (i in 0 until TOPIC_AMOUNT) {
                    val element = exampleTopic[i]
                    repeat(9) { list.add(element) }
                }
                list
            }) {
                val temp = HashMap<String, Int>().apply { exampleTopic.forEach { put(it, 0) } }
                transform {
                    val item =
                        if (temp[it]==0)
                            Material.SNOWBALL
                        else if (temp[it]!! <= voteTopic[it]!!)
                            Material.LIME_STAINED_GLASS_PANE
                        else
                            Material.WHITE_STAINED_GLASS_PANE
                    temp[it] = temp[it]!! + 1
                    ItemUtil.itemStack(item, "&a$it".color)
                }
                onClickItem { _, _, item, _ ->
                    val s = item.first
                    val votedTopic = playerData[player]!![TOPIC_VOTE]?.toString()
                    if (votedTopic != null) voteTopic[votedTopic] = voteTopic[votedTopic]!! - 1
                    voteTopic[s] = voteTopic[s]!! + 1
                    playerData[player]!![TOPIC_VOTE] = s
                    updateMenu()
                }
            }
        })
        playerData[player]!![IS_VOTING_TOPIC] = true
    }

    private fun updateMenu() = joined
        .filter { playerData[it]!![IS_VOTING_TOPIC] != null }
        .forEach { {
            playerData[it]!!.remove(IS_VOTING_TOPIC)
            openMenu(it)
            playerData[it]!![IS_VOTING_TOPIC] = true
        }.delay(plugin, 0) }

}