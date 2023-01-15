package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.NameSpace
import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.singleton.GameServer
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.utils.randomUUID
import java.util.*

class GameInstanceService(
    private val server: GameServer,
    private val gamePlayerService: GamePlayerService,
    component: ComponentService,
) : LayeredComponentService by component as LayeredComponentService {


    init {
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

    fun join(game: Game, player: GPlayer) {
        left(player)
        gamePlayerService.load(player.nameSpace, true)
        player.joinedGame = game
        player.addParents(game)
        game.gameJoined.add(player)
    }

    fun left(player: GPlayer) {
        val joinedGameAtomic = player.joinedGame
        val joinedGame = get(joinedGameAtomic ?: return, ::Game)
        player.removeParents(joinedGame)
        player.joinedGame = null
        joinedGame.gameJoined.remove(player)
        gamePlayerService.unload(player.nameSpace, false)
        // STOPSHIP: ComponentService layer priority 만들어서 NameSpace parents sorting by priority 하고 parents 함수로 감싸기
    }


}