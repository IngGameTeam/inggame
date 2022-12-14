package io.github.inggameteam.minigame.impl

import io.github.inggameteam.bossbar.GBar
import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.GameState
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
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
    : CompetitionImpl(plugin), NoDamage, SimpleGame, CircleSpawn, BarGame {
    override val name get() = "shiritori"

    private lateinit var currentPlayer: GPlayer
    var currentWord = ""
    private val koreanWorldDetector by lazy { KoreanWorldDetector(File(plugin.dataFolder, "kr_korean.db")) }
    override val bar by lazy { GBar(plugin, size=140.0, reversed = true) }
    var wordSemaphore = false
    var chatSemaphore = false

    @Suppress("unused")
    @EventHandler
    fun onBegin(event: GameBeginEvent) {
        if (event.game != this) return
        currentPlayer = joined.hasTags(PTag.PLAY).random()
        var block: () -> Unit = {}
        block = loop@{
            bar.tick = 0
            currentPlayer.damage(100000.0)
            if(gameState === GameState.STOP) return@loop
            nextPlayer()
            bar.startTimer(block)
        }
        bar.startTimer(block)
        addTask({
            bar.update(comp.string("bar", plugin.defaultLanguage).format(currentPlayer, currentWord))
            true
        }.repeat(plugin, 1, 1))
        measureTimeMillis {
            thread { newRandomWord() }
        }.apply { println("koreanWordRandomPick(${this}ms)") }
    }

    fun newRandomWord() {
        wordSemaphore = true
        currentWord = koreanWorldDetector.getRandomKoreanWord()
            .replace("-", " ")
            .replace("^", " ")
        wordSemaphore = false
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
        event.isCancelled = true
        thread {
            while (wordSemaphore && chatSemaphore) {/*wait*/}
            chatSemaphore = true
            if (player != currentPlayer) return@thread
            val isKorean = listOf(
                input,
                input.replace(" ", "^"),
                input.replace(" ", "-"))
                .any { koreanWorldDetector.isKoreanWord(it) }
            if (isKorean && currentWord.substring(currentWord.length - 1) == input.substring(0, 1)) {
                comp.send("correct-word", joined, player, msg)
                ;{
                    bar.tick = 0

                    currentWord = msg
                    nextPlayer()
                }.runNow(plugin)
            } else {
                comp.send("not-correct-word", joined, player, msg)
            }
            chatSemaphore = false
        }
    }

    private fun nextPlayer() {
        bar.tick = 0
        val index = joined.indexOf(currentPlayer)
        val front = joined.subList(0, index)
        val behind = joined.subList(index, joined.size)
        currentPlayer = GPlayerList(GPlayerList(behind).apply { addAll(front) })
            .first { it.hasTag(PTag.PLAY) && currentPlayer != it }
    }

    class KoreanWorldDetector(file: File) {

        var uri = "jdbc:sqlite:${file.absolutePath}"

        fun <T> connect(url: String?, block: (Connection) -> T): T {
            var conn: Connection? = null
            var res: T? = null
            try {
                // create a connection to the database
                conn = DriverManager.getConnection(url)
                println("Connection to SQLite has been established.")
                res = block(conn)
            } catch (e: SQLException) {
                println(e.message)
            } finally {
                try {
                    conn?.close()
                } catch (ex: SQLException) {
                    println(ex.message)
                }
            }
            if (res === null) throw AssertionError("null ")
            return res
        }

        fun hasRow(sql: String?): Boolean {
            try {
                return connect(uri) {
                    val rs: ResultSet?
                    val stmt: Statement = it.createStatement()
                    rs = stmt.executeQuery(sql)
                    rs.next()
                }
            } catch (e: SQLException) {
                println(e.message)
            }
            return false
        }

        fun <T> query(sql: String?, block: (ResultSet) -> T): T {
            try {
                return connect(uri) {
                    var rs: ResultSet? = null
                    val stmt = it.createStatement()
                    rs = stmt.executeQuery(sql)
//                    while (rs.next()) {
//                        empty
//                    }
                    block(rs).apply { rs.close() }
                }
            } catch (e: SQLException) {
                println(e.message)
            }
            throw AssertionError("null")
        }


        fun isKoreanWord(input: String): Boolean {
            return hasRow("SELECT * FROM kr WHERE word = '${input.replace("'", "\'")}'")
        }

        fun getRandomKoreanWord(): String {
            return query(
                "SELECT * FROM kr ORDER BY random() LIMIT 1;"
            ) {
                it.getString("word")?: throw AssertionError("random korean db result is null")
            }
        }
    }

}
