package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.model.InventoryModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.player.ContainerState
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

interface KitOnWait : Wrapper {
    val kitOnWait: InventoryModel?
}

class KitOnWaitImp(wrapper: Wrapper) : SimpleWrapper(wrapper), KitOnWait {
    override val kitOnWait: InventoryModel? by nullable
}

@Deprecated("not referenced")
class KitOnWaitHandler(plugin: IngGamePlugin) : HandleListener(plugin) {
    @Suppress("unused")
    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        if (isNotHandler(player)) return
        val kit = player[::KitOnWaitImp].kitOnWait
        if (player[::GameImp].containerState !== ContainerState.WAIT) return
        player.inventory.contents = kit?.inventory?.contents?: return
    }
}