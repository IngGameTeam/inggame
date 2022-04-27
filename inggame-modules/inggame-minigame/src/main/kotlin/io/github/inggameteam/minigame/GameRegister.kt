package io.github.inggameteam.minigame

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.game
import net.jafama.FastMath
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import io.github.inggameteam.minigame.GameAlert.*

class GameRegister(val gPlugin: GamePlugin, val hubName: String) : HashSet<Game>(), Listener {

    //Hub.NAME
    //partyRegister
    //playerRegister
    //gameSupplierRegister

    @Suppress("Deprecated")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        join(event.player, hubName)
    }

    fun getJoinedGame(player: Player): Game {
        val gPlayer = gPlugin.playerRegister[player]
        return stream().filter { game -> game.joined.contains(gPlayer) }.findFirst().orElse(null)!!
    }

    fun join(player: Player, name: String, joinType: JoinType = JoinType.PLAY) {
        val gPlayer = gPlugin.playerRegister[player]
        if (gPlugin.partyRegister.joinedParty(gPlayer) && name != hubName) {
            if (gPlugin.partyRegister.hasOwnParty(gPlayer)) {
                val joined = gPlugin.partyRegister.getJoined(gPlayer)!!.joined
                joined.filter { getJoinedGame(it.player).name == hubName }.forEach { join(it.player, hubName) }
                joined.forEach { left(it.player, LeftType.DUE_TO_MOVE_ANOTHER_GAME) }
                val game = findOrCreateGame(gPlayer, name)
                joined.forEach { game.joinGame(it, joinType) }
            } else gPlugin.alert(ONLY_LEADER_START).send(gPlugin.console, gPlayer.player)
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
        val gPlayer = player.game
        HashSet(this).stream().filter { game -> game.joined.contains(gPlayer) }.forEach { game ->
            game.leftGame(gPlayer, leftType)
        }
        return true
    }

    fun createGame(name: String,
                   game: Game = gPlugin.gameSupplierRegister[name]!!(newAllocatable())
    ): Game {
        Bukkit.getPluginManager().registerEvents(game, gPlugin)
        add(game)
        return game
    }

    fun removeGame(game: Game) {
        remove(game)
        HandlerList.unregisterAll(game)
    }

    fun newAllocatable(): Sector {
        val list = filter(Game::isAllocated).map(Game::point).toSet()
        val line = FastMath.sqrt(list.size.toDouble()).toInt() + 1
        var x = 1
        while (x <= line) {
            var z = 1
            while (z <= line) {
                if (!list.any { it.equals(x, z) }) return Sector(x, z)
                z++
            }
            x++
        }
        return Sector(1, 1)
    }


}
