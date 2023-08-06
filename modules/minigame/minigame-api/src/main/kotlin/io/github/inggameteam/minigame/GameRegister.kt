package io.github.inggameteam.minigame

import io.github.inggameteam.minigame.GameAlert.ONLY_LEADER_START
import io.github.inggameteam.player.GPlayer
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.sqrt
import kotlin.test.assertNotNull

class GameRegister(
    val plugin: GamePlugin,
    val hubName: String,
    val worldName: List<String>,
    val sectorWidth: Int,
    val sectorHeight: Int,
    ) : HashSet<Game>(), Listener {


    private val sectorFactory = AtomicInteger()
    private val newSector get() = sectorFactory.getAndAdd(1)

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        join(event.player, hubName)
    }

    @Suppress("unused")
    @EventHandler(priority = EventPriority.HIGH)
    fun onQuit(event: PlayerQuitEvent) {
        left(event.player, LeftType.LEFT_SERVER)
    }

    fun getJoinedGame(player: Player): Game {
        val gPlayer = plugin.playerRegister[player]
        return filter { game -> game.joined.contains(gPlayer) }.firstOrNull()
            .apply { assertNotNull(this, "${player.name} joined game is null") }!!
    }

    fun join(player: Player, name: String, joinType: JoinType = JoinType.PLAY, forceCreateGame: Boolean = false) {
        val gPlayer = plugin.playerRegister[player]
        if (plugin.partyRegister.joinedParty(gPlayer) && name != hubName) {
            if (plugin.partyRegister.hasOwnParty(gPlayer)) {
                val joined = plugin.partyRegister.getJoined(gPlayer)!!.joined
                joined.filterNot { getJoinedGame(it).name == hubName }.forEach { join(it, hubName) }
                val game = createGame(name)
                joined.forEach { left(it, LeftType.DUE_TO_MOVE_ANOTHER_GAME) }
                joined.forEach { game.joinGame(it, joinType) }
            } else plugin.component.send(ONLY_LEADER_START, gPlayer)
        } else {
            if (forceCreateGame) {
                val createGame = createGame(name)
                left(player, LeftType.DUE_TO_MOVE_ANOTHER_GAME)
                createGame.joinGame(gPlayer, joinType)
            } else {
                val findOrCreateGame = findOrCreateGame(gPlayer, name)
                left(player, LeftType.DUE_TO_MOVE_ANOTHER_GAME)
                findOrCreateGame.joinGame(gPlayer, joinType)
            }
        }
    }

    fun findOrCreateGame(gPlayer: GPlayer, name: String): Game {
        var game = firstOrNull { g: Game -> name == g.name && g.requestJoin(gPlayer, JoinType.PLAY, false) }
        if (game == null) game = createGame(name)
        return game
    }

    fun left(player: Player, leftType: LeftType): Boolean {
        val gPlayer = plugin[player]
        HashSet(this).filter { game -> game.joined.contains(gPlayer) }.forEach { game ->
            game.leftGame(gPlayer, leftType)
        }
        return true
    }

    fun createGame(name: String, game: Game = plugin.gameSupplierRegister[name](plugin)): Game {
        Bukkit.getPluginManager().registerEvents(game, plugin)
        add(game)
        return game
    }

    fun removeGame(game: Game) {
        remove(game)
        HandlerList.unregisterAll(game)
    }

    fun newAllocatable(world: World): Sector {
        val list = filter(Game::isAllocated).map(Game::point).filter { it.worldOrNull == world }.toSet()
        val line = sqrt(newSector.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return Sector(x, z, world)
                z++
            }
            x++
        }
        return Sector(1, 1, world)
    }


}
