package io.github.inggameteam.player

import io.github.inggameteam.scheduler.ITask
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GPlayer(uuid: UUID) : HashMap<String, Any>(), TagContainer, Player by Bukkit.getPlayer(uuid)!! {
    private val taskList = ArrayList<ITask>()
    fun addTask(task: ITask) {
       taskList.add(task)
    }
    override var tags = HashSet<String>()
    override fun isEmpty() = super.isEmpty()
    val bukkit: Player get() = Bukkit.getPlayer(uniqueId)!!

    fun dispose() {
        taskList.forEach(ITask::cancel)
    }

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        return Bukkit.getPlayer(uniqueId)?.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return bukkit.hashCode()
    }

}

infix fun GPlayer.eq(player: GPlayer?) = player === null || uniqueId == player.uniqueId
