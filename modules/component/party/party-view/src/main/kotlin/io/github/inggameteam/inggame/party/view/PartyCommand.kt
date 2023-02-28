package io.github.inggameteam.inggame.party.view

import io.github.inggameteam.command.MCCommand
import io.github.inggameteam.command.context.CommandContext
import io.github.inggameteam.command.player
import io.github.inggameteam.inggame.party.component.PartyPlayerService
import io.github.inggameteam.inggame.party.handler.PartyHelper
import io.github.inggameteam.inggame.party.handler.PartyRequestHelper
import io.github.inggameteam.inggame.party.wrapper.PartyPlayerImp
import io.github.inggameteam.inggame.utils.IngGamePlugin
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class PartyCommand(
    val partyPlayerService: PartyPlayerService,
    val partyHelper: PartyHelper,
    partyRequestHelper: PartyRequestHelper,
) {
    fun partyCommand(plugin: IngGamePlugin) {
        MCCommand(plugin as JavaPlugin) {
            command("party") {
                thenExecute("create") {

                    partyPlayer
                }
            }
        }
    }

    val CommandContext<CommandSender>.partyPlayer get() = partyPlayerService[player.uniqueId, ::PartyPlayerImp]
}
