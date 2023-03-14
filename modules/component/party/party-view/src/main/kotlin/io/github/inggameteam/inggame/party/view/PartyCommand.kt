package io.github.inggameteam.inggame.party.view

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.context.CommandContext
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.handler.PartyHelper
import io.github.inggameteam.inggame.party.handler.PartyRequestHelper
import io.github.inggameteam.inggame.party.wrapper.PartyPlayerImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import io.github.inggameteam.inggame.utils.Listener
import io.github.inggameteam.inggame.utils.event.IngGamePluginEnableEvent
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.plugin.java.JavaPlugin

class PartyCommand(
    val partyPlayerService: PartyPlayerService,
    val partyHelper: PartyHelper,
    val partyRequestHelper: PartyRequestHelper, plugin: IngGamePlugin,
) : Listener(plugin) {

    @Suppress("unused")
    @EventHandler
    fun onEnable(event: IngGamePluginEnableEvent) {
        partyCommand(event.plugin)
    }

    private fun partyCommand(plugin: IngGamePlugin): Unit = partyHelper.run {
        MCCommand(plugin as JavaPlugin) {
            command("party", "p") {
                execute { assertPlayer(dispatcher, args[0]) { partyRequestHelper.invitePlayer(dispatcher, it) } }
                thenExecute("all") { partyRequestHelper.inviteAll(dispatcher) }
                thenExecute("create") { createParty(dispatcher) }
                thenExecute("rename") { renameParty(dispatcher, args[0]) }
                thenExecute("visible") { visible(dispatcher) }
                thenExecute("promote") { assertPlayer(dispatcher, args[0]) { promote(dispatcher, it) } }
                thenExecute("kick") { assertPlayer(dispatcher, args[0]) { kick(dispatcher, it) } }
                thenExecute("ban") { assertPlayer(dispatcher, args[0]) { ban(dispatcher, it) } }
                thenExecute("unban") { assertPlayer(dispatcher, args[0]) { unban(dispatcher, it) } }
                thenExecute("list") { listMembers(dispatcher) }
                thenExecute("help") { partyHelp(dispatcher) }
                thenExecute("test") {
                    player.sendMessage(player.itemInHand.type.toString())
                }
            }
        }
    }

    private val CommandContext<CommandSender>.dispatcher get() = partyPlayerService[player.uniqueId, ::PartyPlayerImp]
}
