package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.randomUUID

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    private val playerService: PlayerService,
    component: ComponentService,
) : LayeredComponentService by component as LayeredComponentService {


    init {
        println("-".repeat(30))
        println("-".repeat(30))
        println("-".repeat(30))
        println("-".repeat(30))
        println("-".repeat(30))
        println("-".repeat(30 ))
        server.hub = component.get(createGame(server::hub.name).name, ::Game)
    }

    fun hasGame(nameSpace: Any): Boolean = getOrNull(nameSpace) !== null

    fun createGame(name: Any): NameSpace {
        val uuid = randomUUID()
        load(uuid)
        return get(uuid).apply { parents.add(name) }
    }

    fun removeGame(uuid: Game) {
        unload(uuid, false)
    }

    fun join(game: Game, key: Any) {
        left(key)
        gamePlayerService.load(key, true)
        val gPlayer = gamePlayerService.get(key, ::GPlayer)
        gPlayer.joinedGame = game
        gPlayer.addParents(game)
        game.gameJoined.add(gPlayer)
    }

    fun left(key: Any) {
        val gPlayer = gamePlayerService.get(key, ::GPlayer)
        val joinedGameAtomic = gPlayer.joinedGame
        val joinedGame = get(joinedGameAtomic ?: return, ::Game)
        joinedGame.gameJoined.remove(key)
        gamePlayerService.unload(key, false)
    }


}