package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.CircleSpawn
import io.github.inggameteam.minigame.base.CompetitionImpl
import io.github.inggameteam.minigame.base.NoDamage
import io.github.inggameteam.minigame.base.SimpleGame
import io.github.inggameteam.minigame.event.GameBeginEvent
import io.github.inggameteam.minigame.event.GameLeftEvent
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.GPlayerList
import io.github.inggameteam.player.hasTags
import io.github.inggameteam.scheduler.repeat
import io.github.inggameteam.scheduler.runNow
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.io.File
import java.sql.*
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


class Shiritori(plugin: GamePlugin)
    : CompetitionImpl(plugin), NoDamage, SimpleGame, CircleSpawn {
    override val name get() = "shiritori"

    private lateinit var currentPlayer: GPlayer
    lateinit var currentWord: String
    private val koreanWorldDetector by lazy { KoreanWorldDetector(File(plugin.dataFolder, "kr_korean.db")) }
    val bar by lazy { GBar(plugin, 750.0) }

    @Suppress("unused")
    @EventHandler
    fun onBegin(event: GameBeginEvent) {
        if (event.game != this) return
        currentPlayer = joined.hasTags(PTag.PLAY).random()
        addTask({
            bar.update(comp.string("bar", plugin.defaultLanguage).format(currentPlayer, currentWord))
            true
        }.repeat(plugin, 1, 1))
        measureTimeMillis {
            currentWord = koreanWorldDetector.getRandomKoreanWord()
        }.apply { println("koreanWordRandomPick(${this}ms)") }
    }

    @Suppress("unused")
    @EventHandler
    fun onLeft(event: GameLeftEvent) {
        if (gameState !== GameState.PLAY || !isJoined(event.player)) return
        nextPlayer()
    }

    @Suppress("unused")
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val player = plugin[event.player]
        if (gameState !== GameState.PLAY || !isJoined(player)) return
        val msg = event.message
        val input = msg
        if (player != currentPlayer) {
            comp.send("not-current-player", player)
        }
        thread {
            if (player != currentPlayer) return@thread
            val isKorean = koreanWorldDetector.isKoreanWord(input)
            if (isKorean) {
                comp.send("correct-word", joined, player, msg)
                ;{ nextPlayer() }.runNow(plugin)
            } else {
                comp.send("not-correct-word", joined, player, msg)
            }
        }
    }

    private fun nextPlayer() {
        val index = joined.indexOf(currentPlayer)
        val front = joined.subList(0, index)
        val behind = joined.subList(index, joined.size)
        currentPlayer = GPlayerList(GPlayerList(behind).apply { addAll(front) })
            .first { it.hasTag(PTag.PLAY) && currentPlayer != it }
    }

    class KoreanWorldDetector(file: File) {

        var uri = "jdbc:sqlite:${file.absolutePath}"

        fun connect(url: String?): Connection? {
            var conn: Connection? = null
            try {
                // create a connection to the database
                conn = DriverManager.getConnection(url)
                println("Connection to SQLite has been established.")
            } catch (e: SQLException) {
                println(e.message)
            } finally {
                try {
                    conn?.close()
                } catch (ex: SQLException) {
                    println(ex.message)
                }
            }
            return conn
        }

        fun hasRow(sql: String?): Boolean {
            val rs: ResultSet?
            try {
                val conn = connect(uri)
                val stmt: Statement = conn!!.createStatement()
                rs = stmt.executeQuery(sql)
                return rs.next()
            } catch (e: SQLException) {
                println(e.message)
            }
            return false
        }

        fun query(sql: String?): ResultSet? {
            var rs: ResultSet? = null
            try {
                val conn = connect(uri)
                val stmt = conn!!.createStatement()
                rs = stmt.executeQuery(sql)
                while (rs.next()) {
                    //empty
                }
            } catch (e: SQLException) {
                println(e.message)
            }
            return rs
        }


        fun isKoreanWord(input: String): Boolean {
            return hasRow("SELECT * FROM kr WHERE word = '${input.replace("'", "\'")}'")
        }

        fun getRandomKoreanWord(): String {
            return query(
                "SELECT * FROM kr\n" +
                        "WHERE id = (SELECT seq FROM sqlite_sequence WHERE name='kr' ORDER BY random() LIMIT 1);"
            )
                ?.getString("word")?: throw AssertionError("random korean db result is null")
        }
    }

}
