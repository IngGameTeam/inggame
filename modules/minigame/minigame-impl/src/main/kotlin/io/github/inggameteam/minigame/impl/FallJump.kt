package io.github.inggameteam.minigame.impl

import io.github.inggameteam.minigame.GamePlugin
import io.github.inggameteam.minigame.PTag
import io.github.inggameteam.minigame.base.*
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.hasTags
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent

class FallJump(plugin: GamePlugin) : ClearTheBlocksBelow, SimpleGame, CompetitionImpl(plugin), NoDamage, NoBlockBreak {
    override val name get() = "fall-jump"

    override fun beginGame() {
        super.beginGame()
        joined.hasTags(PTag.PLAY).forEach { addUp(it)}
    }

    override fun listClearBlock(location: Location) = ArrayList<Block>().apply {
        for (x in location.blockX - 1..location.blockX + 1)
            for (z in location.blockZ - 1..location.blockZ + 1)
                add(location.clone().apply { setX(x.toDouble()); setZ(z.toDouble()) }.block)
    }.filter { it.type !== Material.AIR }

    override fun belowCleared(player: GPlayer) = addUp(player)

    fun addUp(player: Player) {
        player.velocity = player.velocity.apply { y=1.4 }
    }
}