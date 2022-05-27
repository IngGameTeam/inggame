package io.github.inggameteam.minigame.impl

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.*
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask

class Tutorial(plugin: GamePlugin) : SectionalImpl(plugin), SpawnPlayer, SimpleGame {
    override val name get() = "tutorial"
    override val recommendedStartPlayersAmount get() = 1

    var count = 1
    var stopTick = 0


    override fun beginGame() {
        super.beginGame()
        joined.hasTags(PTag.PLAY).forEach { player ->
            val lang = player.lang(plugin)
            stopTick = Integer.parseInt(comp.string("stop", lang))
            gameTask = ITask.repeat(plugin, 1, 1, {
                if (count >= stopTick) {
                    joined[0].addTag(PTag.DEAD)
                    leftGame(joined[0], LeftType.GAME_STOP)
                    return@repeat
                }
                comp.stringOrNull("title${count}", lang)?.split(" ")?.apply {
                    val fadeIn = get(0).toInt()
                    val stay = get(1).toInt()
                    val fadeOut = get(2).toInt()
                    (this.subList(3, this.size).joinToString(" ")).split("-").apply {
                        val subTitle = if (size != 1) get(1) else get(0)
                        val title = if (size != 1) get(0).ifEmpty { " " } else " "
                        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
                    }
                }
                comp.stringOrNull("sound$count", lang)?.split(" ")?.apply {
                    player.playSound(player.location,
                        this[0],
                        Float.MAX_VALUE,
                        1f)
                }
                comp.stringOrNull("stopSound$count", lang)?.split(" ")?.apply {
                    player.stopSound(this[0])
                }
                count++
            })
        }    }


}