package io.github.inggameteam.minigame

import io.github.inggameteam.minigame.GameAlert.ONLY_LEADER_START
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.utils.IntVector
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.math.sqrt

class GameRegister(
    val plugin: GamePlugin,
    val hubName: String,
    val worldName: String,
    val worldSize: IntVector,
    ) : HashSet<Game>(), Listener {

    //Hub.NAME
    //partyRegister
    //playerRegister
    //gameSupplierRegister

    val world: World? get() = Bukkit.getWorld(worldName)

    @Suppress("Deprecated")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        join(event.player, hubName)
    }

    fun getJoinedGame(player: Player): Game {
        val gPlayer = plugin.playerRegister[player]
        return stream().filter { game -> game.joined.contains(gPlayer) }.findFirst().orElse(null)!!
    }

    fun join(player: Player, name: String, joinType: JoinType = JoinType.PLAY) {
        val gPlayer = plugin.playerRegister[player]
        if (plugin.partyRegister.joinedParty(gPlayer) && name != hubName) {
            if (plugin.partyRegister.hasOwnParty(gPlayer)) {
                val joined = plugin.partyRegister.getJoined(gPlayer)!!.joined
                joined.filter { getJoinedGame(it).name == hubName }.forEach { join(it, hubName) }
                joined.forEach { left(it, LeftType.DUE_TO_MOVE_ANOTHER_GAME) }
                val game = findOrCreateGame(gPlayer, name)
                joined.forEach { game.joinGame(it, joinType) }
            } else plugin.component.send(ONLY_LEADER_START, gPlayer)
        } else {
            left(player, LeftType.DUE_TO_MOVE_ANOTHER_GAME)
            findOrCreateGame(gPlayer, name).joinGame(gPlayer, joinType)
        }
    }

    fun findOrCreateGame(gPlayer: GPlayer, name: String): Game {
        var game =
            stream().filter { g: Game -> name == g.name && g.requestJoin(gPlayer, JoinType.PLAY, false) }
                .findFirst().orElse(null)
        if (game == null) game = createGame(name)
        return game
    }

    fun left(player: Player, leftType: LeftType): Boolean {
        val gPlayer = plugin[player]
        HashSet(this).stream().filter { game -> game.joined.contains(gPlayer) }.forEach { game ->
            game.leftGame(gPlayer, leftType)
        }
        return true
    }

    fun createGame(name: String,
                   game: Game = plugin.gameSupplierRegister[name](plugin, newAllocatable())
    ): Game {
        Bukkit.getPluginManager().registerEvents(game, plugin)
        add(game)
        return game
    }

    fun removeGame(game: Game) {
        remove(game)
        HandlerList.unregisterAll(game)
    }

    fun newAllocatable(): IntVector {
        val list = filter(Game::isAllocated).map(Game::point).toSet()
        val line = sqrt(list.size.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return IntVector(x, z)
                z++
            }
            x++
        }
        return IntVector(1, 1)
    }


}
