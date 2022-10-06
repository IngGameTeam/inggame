package io.github.inggameteam.minigame.base

import io.github.inggameteam.alert.Lang.lang
import io.github.inggameteam.minigame.Game
import io.github.inggameteam.scheduler.delay
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

const val LEAVE_ITEM = "leave"
const val CLICK_PAUSED = "clickPaused"
interface LeaveWhenYouClickLeaveItem : Game {

    @Suppress("unused")
    @EventHandler
    fun interact(event: PlayerInteractEvent) = click(event.player, event.item, event)
    @Suppress("unused")
    @EventHandler
    fun interactEntity(event: PlayerInteractEntityEvent) =
        click(event.player, event.player.inventory.getItem(event.hand), event)

    private fun click(player: Player, item: ItemStack?, event: Cancellable?) {
        if (!isJoined(player)) return
        if (item === null) return
        if (comp.item(LEAVE_ITEM, player.lang(plugin)).isSimilar(item)) {
            player.inventory.heldItemSlot = 0
            plugin[player].apply {
                this[CLICK_PAUSED] = true
                addTask({ this.remove(CLICK_PAUSED) }.delay(plugin, 4))
            }
            plugin.gameRegister.join(player, plugin.gameRegister.hubName)
            if (event != null) event.isCancelled = true
        }
    }



}
