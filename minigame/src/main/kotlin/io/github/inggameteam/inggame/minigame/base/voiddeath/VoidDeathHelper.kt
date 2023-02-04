package io.github.inggameteam.inggame.minigame.base.voiddeath

import io.github.inggameteam.inggame.component.Handler.Companion.isHandler
import io.github.inggameteam.inggame.component.Handler.Companion.isNotHandler
import io.github.inggameteam.inggame.minigame.base.player.GPlayer
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalHandler
import io.github.inggameteam.inggame.minigame.base.sectional.SectionalImp

class VoidDeathHelper {

    fun testVoidDeath(player: GPlayer): Boolean {
        val playerY = player.location.y
        val joinedGame = player.joinedGame[::SectionalImp]
        val voidDeath = player[::VoidDeathImp].voidDeath
        if (player.isNotHandler(VoidDeathHandler::class)) return false
        return if (player.isHandler(SectionalHandler::class)) {
            playerY <= joinedGame.center.y + voidDeath
        } else {
            playerY <= voidDeath
        }
    }

}