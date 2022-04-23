package io.github.inggameteam.scheduler

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class ITask {
    constructor(vararg tasks: BukkitTask) {
        this.tasks.addAll(tasks.map { SingleTask(it) })
    }
    constructor(vararg tasks: Int) {
        this.tasks.addAll(tasks.map { SingleTask(taskId = it) })
    }
    val tasks = ArrayList<SingleTask>()
    fun cancel() {
        tasks.forEach {
            val bukkitTask = it.bukkitTask
            if (bukkitTask === null) Bukkit.getScheduler().cancelTask(it.taskId)
            else if (!bukkitTask.isCancelled) bukkitTask.cancel()
        }
    }
    data class SingleTask(val bukkitTask: BukkitTask? = null, val taskId: Int = if(bukkitTask === null) -1 else bukkitTask.taskId)
}


fun (() -> Unit).delay(plugin: Plugin, delay: Long) =
    ITask(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, delay))
fun (() -> Boolean).repeat(plugin: Plugin, delay: Long, period: Long) =
    ITask(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, object : BukkitRunnable() {
        override fun run() {
            if (!this@repeat()) {
                cancel()
            }
        }
    }, delay, period))
fun (() -> Unit).runNow(plugin: Plugin) = ITask(Bukkit.getScheduler().runTask(plugin, this))
fun (() -> Unit).async(plugin: Plugin) = ITask(Bukkit.getScheduler().runTaskAsynchronously(plugin, this))
