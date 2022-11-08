package io.github.inggameteam.minigame.handle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.vexsoftware.votifier.model.VotifierEvent
import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.mongodb.impl.UserContainer
import io.github.inggameteam.mongodb.impl.Votes
import io.github.inggameteam.scheduler.async
import io.github.inggameteam.utils.fastUUID
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*

class RewardVote(val plugin: AlertPlugin, val user: UserContainer, val votes: Votes) : HandleListener(plugin) {

    private val rewardAmount = 1000L

    @Suppress("unused")
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val vote = votes.getVote(event.player.uniqueId)
        if (vote != -1) {
            rewardOnline(event.player, vote)
        }
    }

    @Suppress("unused")
    @EventHandler
    fun onVote(event: VotifierEvent) {
        val username = event.vote.username
        val player = Bukkit.getPlayer(username)
        if (player !== null) {
            rewardOnline(player)
        } else {
            rewardOffline(username)
        }
    }

    private fun rewardOnline(player: Player, multiply: Int = 1) {
        val amount = rewardAmount * multiply
        user[player].point += amount
        plugin.component.send("VOTE", plugin[player], amount)
    }

    private fun rewardOffline(playerName: String) {
        ;{
            val uuid = getUUID(playerName).fastUUID()
            votes.addVote(uuid)
        }.async(plugin)
    }

    private fun getUUID(name: String): String {
        var uuid: String
        try {
            val `in` =
                BufferedReader(InputStreamReader(URL("https://api.mojang.com/users/profiles/minecraft/$name").openStream()))
            uuid = (JsonParser().parse(`in`) as JsonObject).get("id").toString().replace("\"", "")
            uuid = uuid.replace("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(), "$1-$2-$3-$4-$5")
            `in`.close()
        } catch (e: Exception) {
            println("Unable to get UUID of: $name!")
            uuid = "er"
        }
        return uuid
    }


}