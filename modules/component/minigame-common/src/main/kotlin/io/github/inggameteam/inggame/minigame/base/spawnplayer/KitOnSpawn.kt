package io.github.inggameteam.inggame.minigame.base.spawnplayer

import io.github.inggameteam.inggame.component.HandleListener
import io.github.inggameteam.inggame.component.model.InventoryModel
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.minigame.base.game.GameImp
import io.github.inggameteam.inggame.minigame.base.game.event.GPlayerSpawnEvent
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.event.EventHandler

interface KitOnSpawn : Wrapper {

    val kitOnSpawn: HashMap<String, InventoryModel>

}

class KitOnSpawnImp(wrapper: Wrapper) : SimpleWrapper(wrapper), KitOnSpawn {
    override val kitOnSpawn: HashMap<String, InventoryModel> by nonNull
}

class KitOnSpawnHandler(plugin: IngGamePlugin) : HandleListener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onSpawn(event: GPlayerSpawnEvent) {
        val player = event.player
        if (isNotHandler(player)) return
        val state = player[::GameImp].gameState.name
        player.inventory.contents = player[::KitOnSpawnImp].kitOnSpawn[state]!!.inventory.contents
    }

}