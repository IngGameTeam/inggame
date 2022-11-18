package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit

class Quiz(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame, SpawnPlayer, BarGame, NoDamage {
    override val name get() = "quiz"
    override val bar = GBar(plugin)
    var timeSize = 13 * 20.0
    var time = timeSize
    var result = false
    var quizMsg = ""
    lateinit var oQz: ArrayList<String>
    lateinit var xQz: ArrayList<String>
    var isQuizDone = false

    override fun beginGame() {
        super.beginGame()
        oQz = ArrayList(comp.stringList("O", plugin.defaultLanguage))
        xQz = ArrayList(comp.stringList("X", plugin.defaultLanguage))
        generateQuestion()
        gameTask = ITask.repeat(plugin, 1, 1, {
            if (time <= 0) {
                time = timeSize
                broadcastAnswer()
                killFailedPlayers()
                generateQuestion()
            } else time--
            if (gameState === GameState.PLAY) bar.update(progress = time / timeSize)
        })
    }

    private fun generateQuestion() {
        quizMsg = listOf(oQz, xQz).filter { it.isNotEmpty() }
            .apply {
                if (isEmpty()) {
                    isQuizDone = true
                    stop(false)
                    return
                }
            }
            .random().run {
                result = this === oQz
                random().apply {
                    (if (result) oQz else xQz).remove(this)
                }
            }
        comp.send("quiz", joined, quizMsg)
    }

    private fun broadcastAnswer() {
        comp.send("result", joined, if (result) "O" else "X")
    }

    private fun killFailedPlayers() {
        val double = getLocation("point").x
        val playersToDie = joined.hasTags(PTag.PLAY)
            .filter { ((it.location.x) < double) == result }.toList()
        playersToDie.forEach { it.apply { addTag(PTag.DEAD) } }
        requestStop()
        if (gameState == GameState.PLAY) {
            playersToDie.forEach { it.apply { removeTag(PTag.PLAY) } }
            playersToDie.forEach { Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(it)) }
        }
    }

    override fun rewardPoint(player: GPlayer): Int {
        if (isQuizDone) {
            return 5000
        }
        return super.rewardPoint(player)
    }

    override fun calcWinner() {
        if (isQuizDone) {
            comp.send("quiz-done", joined)
            return
        }
        super.calcWinner()
    }



}