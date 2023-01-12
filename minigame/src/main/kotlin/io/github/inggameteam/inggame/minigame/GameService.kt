package io.github.inggameteam.inggame.minigame

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentService
import io.github.inggameteam.inggame.component.delegate.get
import io.github.inggameteam.inggame.minigame.wrapper.Server
import io.github.inggameteam.inggame.minigame.wrapper.game.Game
import io.github.inggameteam.inggame.minigame.wrapper.player.GPlayer
import io.github.inggameteam.inggame.player.PlayerService
import io.github.inggameteam.inggame.utils.randomUUID
import java.util.*

class GameService(
    private val server: Server,
    private val playerService: PlayerService,
    component: ComponentService
) : LayeredComponentService by component as LayeredComponentService {

    fun createGame() {
        val uuid = randomUUID()
        load(uuid)
    }

    fun removeGame(uuid: UUID) {
        unload(uuid, false)
    }

    fun join(game: UUID, player: UUID) {
        left(player)
        val gPlayer = playerService.get(player, ::GPlayer)
        gPlayer.joinedGame = game
        val targetGame = get(game, ::Game)
        targetGame.joined.add(player)
    }

    fun left(player: UUID) {
        val gPlayer = playerService.get(player, ::GPlayer)
        val joinedGame = get(gPlayer.joinedGame?: return, ::Game)
        gPlayer.joinedGame = null
        joinedGame.joined.remove(player)
    }


}