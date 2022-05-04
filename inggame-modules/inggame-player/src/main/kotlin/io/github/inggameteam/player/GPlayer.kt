package io.github.inggameteam.player

import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GPlayer(uuid: UUID) : HashMap<String, Any>(), TagContainer,
    Player by Bukkit.getPlayer(uuid)!! {
    val taskList = ArrayList<ITask>()
    override var tags = HashSet<String>()
    override fun isEmpty() = super.isEmpty()

    fun dispose() {
        taskList.forEach(ITask::cancel)
    }

    override fun toString(): String = name


}

infix fun GPlayer.eq(player: GPlayer?) = player === null || uniqueId == player.uniqueId
