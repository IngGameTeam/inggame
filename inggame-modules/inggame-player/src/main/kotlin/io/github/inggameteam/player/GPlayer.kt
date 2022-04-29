package io.github.inggameteam.player

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.alert.api.AlertReceiver
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class GPlayer(uuid: UUID) : HashMap<String, Any>(), AlertReceiver<Player>, TagContainer {
    val player = Bukkit.getPlayer(uuid)!!
    val taskList = ArrayList<ITask>()
    override var tags = HashSet<PTag>()

    fun dispose() {
        taskList.forEach(ITask::cancel)
    }

    fun teleport(location: Location): Boolean {
        return player.teleport(location)
    }


    override fun toString(): String = player.name


    override fun receive(sender: UUID, t: Player, alert: Alert<Player>): Boolean {
        return true
    }

}

val Player.game get() = GPlayer(uniqueId)