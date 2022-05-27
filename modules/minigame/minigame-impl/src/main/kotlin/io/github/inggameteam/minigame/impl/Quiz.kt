package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.minigame.event.GPlayerDeathEvent
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit

class Quiz(plugin: GamePlugin) : CompetitionImpl(plugin), SimpleGame, SpawnPlayer, BarGame, NoDamage {
    override val name get() = "quiz"
    override val bar = GBar(plugin)
    var timeSize = 100.0
    var time = timeSize
    var result = false
    var quizMsg = ""
    lateinit var oQz: List<String>
    lateinit var xQz: List<String>

    override fun beginGame() {
        super.beginGame()
        oQz = comp.stringList("O", plugin.defaultLanguage)
        xQz = comp.stringList("X", plugin.defaultLanguage)
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
                    stop(false)
                    return
                }
            }
            .random().apply { result = this === oQz }.random()
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
        stopCheck()
        if (gameState == GameState.PLAY) {
            playersToDie.forEach { it.apply { removeTag(PTag.PLAY) } }
            playersToDie.forEach { Bukkit.getPluginManager().callEvent(GPlayerDeathEvent(it)) }
        }
    }



}