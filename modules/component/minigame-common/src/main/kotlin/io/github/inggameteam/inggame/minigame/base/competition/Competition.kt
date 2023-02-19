package io.github.inggameteam.inggame.minigame.base.competition

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.Game
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.event.GPlayerDeathEvent

interface Competition : Wrapper {
    val stopCheckPlayer: Int
}

class CompetitionImp(wrapper: Wrapper) : SimpleWrapper(wrapper), Competition {
    override val stopCheckPlayer: Int by nonNull
}

interface CompetitionHandler {

    fun sendDeathMessage(player: GPlayer, killer: GPlayer?)

    fun handleDeath(event: GPlayerDeathEvent)

    fun requestStop(game: Game)

    fun calcWinner(game: Game)

}