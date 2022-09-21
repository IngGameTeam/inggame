package io.github.inggameteam.item.impl

import io.github.inggameteam.alert.AlertPlugin
import io.github.inggameteam.api.HandleListener
import io.github.inggameteam.item.api.ItemComponentGetter
import io.github.inggameteam.mongodb.impl.PurchaseContainer
import io.github.inggameteam.scheduler.repeat
import org.bukkit.Particle

class ParticlePurchase(override val plugin: AlertPlugin, val purchase: PurchaseContainer) : HandleListener(plugin),
    ItemComponentGetter
{

    init {
        {
            plugin.playerRegister.values.forEach { player ->

                val playerPurchase = purchase[player]
                itemComp.stringList("particles", plugin.defaultLanguage).forEach { particle ->
                    if (playerPurchase[particle].amount == 1) {
                        player.spawnParticle(
                            Particle.valueOf(itemComp.string("$particle-particle", plugin.defaultLanguage))
                            , player.location, 1)
                    }
                }

            }
            true
        }.repeat(plugin, 1, 1)
    }

}
