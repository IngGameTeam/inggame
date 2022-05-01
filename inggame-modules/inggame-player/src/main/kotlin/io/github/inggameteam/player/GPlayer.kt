package io.github.inggameteam.player

import io.github.inggameteam.alert.api.Alert
import io.github.inggameteam.alert.api.AlertReceiver
import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class GPlayer(uuid: UUID) : HashMap<String, Any>(), AlertReceiver<Player>, TagContainer,
    Player by Bukkit.getPlayer(uuid)!! {
    val taskList = ArrayList<ITask>()
    override var tags = HashSet<String>()
    override fun isEmpty() = super.isEmpty()

    fun dispose() {
        taskList.forEach(ITask::cancel)
    }

    override fun toString(): String = name


    override fun receive(sender: UUID, t: Player, alert: Alert<Player>): Boolean {
        return true
    }

}

val Player.game get() = GPlayer(uniqueId)