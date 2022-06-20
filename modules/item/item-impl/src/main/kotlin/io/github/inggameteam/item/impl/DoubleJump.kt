package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.Interact
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.scheduler.ITask

class DoubleJump(override val plugin: AlertPlugin) : Interact, HandleListener(plugin) {

    override val name get() = "double-jump"

    override fun use(name: String, player: GPlayer) {
        player.apply {
            val tasks = ArrayList<() -> Unit>()
            val max = 10
            val char = "|"
            if (player[COOL_TAG] !== null) return
            player[COOL_TAG] = true
            for (i in 0..max) tasks.add {
                itemComp.send("BAR_COOL_DOWN", player, char.repeat(i), char.repeat(max - i))
            }
            tasks.add { player.remove(COOL_TAG) }
            player.taskList.add(ITask.repeat(plugin, 1, 1, *tasks.toTypedArray()))
            velocity = location.direction.multiply(1.25).apply { y = 0.6 }
        }

    }

    companion object {
        const val COOL_TAG = "DoubleJumpCoolDown"
    }

}