package io.github.inggameteam.party

import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.game
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.ColorUtil.color
import io.github.inggameteam.utils.ItemUtil
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import io.github.inggameteam.party.PartyAlert.*

class PartyUI(val plugin: PartyPlugin) : CommandExecutor, TabCompleter {
    private fun sendUsage(sender: CommandSender) = sender.sendMessage("&cUsage: /p [player]".color())


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            if (args[0].equals("join", ignoreCase = true) && sender is Player && sender.isOp) {
                val player = Bukkit.getPlayer(args[1])!!
                val gPlayer = player.game
                if (!plugin.partyRegister.joinedParty(gPlayer)) return true
                plugin.partyRegister.getJoined(gPlayer)?.join((
                    if (args.size >= 3) Bukkit.getPlayerExact(args[2])!! else sender
                ).game)
                updateParty()
                return true
            }
            if (args[0].equals("create", ignoreCase = true) && sender is Player) {
                val gPlayer = sender.game
                plugin.partyRegister.createParty(gPlayer)
                updateParty()
                return true
            }
            if (args[0].equals("rename", ignoreCase = true) && sender is Player) {
                val newName = args.copyOfRange(1, args.size).joinToString(" ")
                val gSender = sender.game
                val joinedParty = plugin.partyRegister.getJoined(gSender)
                if (joinedParty == null) {
                    sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                    return true
                }
                if (!plugin.partyRegister.hasOwnParty(gSender)) {
                    sender.sendMessage("&c파티 리더만 파티 이름을 개명할 수 있습니다".color)
                    return true
                }
                if (newName.length > 20) {
                    sender.sendMessage("&c파티 이름은 20자를 초과할 수 없습니다".color)
                    return true
                }
                val beforeName = joinedParty.name
                if (newName.isEmpty()) {
                    joinedParty.resetName()
                } else {
                    joinedParty.name = newName.color
                    joinedParty.renamed = true
                }
                joinedParty.joined.receiveAll(plugin.console, plugin.alert(PARTY_RENAMED), gSender, beforeName, joinedParty.name)
                updateParty()
                return true
            }
            if (args[0].equals("visible", ignoreCase = true) && sender is Player) {
                val gSender = sender.game
                if (plugin.partyRegister.hasOwnParty(gSender)) {
                    val joinedParty = plugin.partyRegister.getJoined(gSender)!!
                    joinedParty.opened = !joinedParty.opened
                    if (joinedParty.opened) {
                        sender.sendMessage("&a이제 누구나 내 파티에 참여할 수 있습니다".color)
                    } else {
                        sender.sendMessage("&a이제 초대한 사람만 내 파티에 참여할 수 있습니다".color)
                    }
                    updateParty()
                }
                return true
            }
            if (args.size >= 2 && args[0].equals("promote", ignoreCase = true) && sender is Player) {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    sender.sendMessage("&c플레이어가 없습니다".color)
                    return true
                }
                val gPlayer = player.game
                val gSender = sender.game
                if (sender == player) {
                    sender.sendMessage("&c당신을 리더로 위임할 수는 없습니다".color)
                    return true
                }
                if (plugin.partyRegister.hasOwnParty(gSender)) {
                    val party = plugin.partyRegister.getJoined(gSender)!!
                    if (!party.joined.contains(gPlayer)) {
                        sender.sendMessage("&c플레이어가 파티에 없습니다".color)
                        return true
                    }
                    gPlayer.player.sendMessage("&a${party.leader}이(가) 당신을 ${party.name} 방장으로 위임했습니다".color)
                    party.joined.receiveAll(plugin.console, plugin.alert(PARTY_PROMOTED), gPlayer, party)
                    party.joined.remove(gPlayer)
                    party.joined.add(0, gPlayer)
                    updateParty()
                } else if (plugin.partyRegister.joinedParty(gSender)) {
                    sender.sendMessage("&c파티 리더만 위임할 수 있습니다".color)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args.size >= 2 && args[0].equals("kick", ignoreCase = true) && sender is Player) {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    sender.sendMessage("&c플레이어가 없습니다".color)
                    return true
                }
                val gPlayer = player.game
                val gSender = sender.game
                if (sender == player) {
                    sender.sendMessage("&c자신을 추방할 수는 없습니다".color)
                    return true
                }
                if (plugin.partyRegister.hasOwnParty(gSender)) {
                    val party = plugin.partyRegister.getJoined(gSender)!!
                    if (!party.joined.contains(gPlayer)) {
                        sender.sendMessage("&c플레이어가 파티에 없습니다".color)
                        return true
                    }
                    party.joined.remove(gPlayer)
                    gPlayer.player.sendMessage("&a${party.leader}이(가) 당신을 ${party.name}에서 추방했습니다".color)
                    party.joined.receiveAll(plugin.console, plugin.alert(PARTY_KICKED), gPlayer, party)
                    plugin.partyRequestRegister.removeIf { it.party == party && it.sender == gPlayer }
                    updateParty()
                } else if (plugin.partyRegister.joinedParty(gSender)) {
                    sender.sendMessage("&c파티 리더만 추방시킬 수 있습니다".color)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args.size >= 2 && args[0].equals("ban", ignoreCase = true) && sender is Player) {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    sender.sendMessage("&c플레이어가 없습니다".color)
                    return true
                }
                val gPlayer = player.game
                val gSender = sender.game
                if (sender == player) {
                    sender.sendMessage("&c자신을 차단할 수는 없습니다".color)
                    return true
                }
                if (plugin.partyRegister.hasOwnParty(gSender)) {
                    val party = plugin.partyRegister.getJoined(gSender)!!
                    if (!party.joined.contains(gPlayer)) {
                        sender.sendMessage("&c플레이어가 파티에 없습니다".color)
                        return true
                    }
//                    party.left(gPlayer)
                    party.joined.remove(gPlayer)
                    party.banList.add(gPlayer.player.uniqueId)
                    gPlayer.player.sendMessage("&a${party.leader}이(가) 당신을 ${party.name}에서 차단했습니다".color)
                    party.joined.receiveAll(plugin.console, plugin.alert(PARTY_BANNED), gPlayer, party)
                    plugin.partyRequestRegister.removeIf { it.party == party && it.sender == gPlayer }
                    updateParty()
                } else if (plugin.partyRegister.joinedParty(gSender)) {
                    sender.sendMessage("&c파티 리더만 차단할 수 있습니다".color)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args.size >= 2 && args[0].equals("unban", ignoreCase = true) && sender is Player) {
                val player = Bukkit.getPlayerExact(args[1])
                if (player == null) {
                    sender.sendMessage("&c플레이어가 없습니다".color)
                    return true
                }
                val gPlayer = player.game
                val gSender = sender.game
                if (sender == player) {
                    sender.sendMessage("&c자신을 차단 해제할 수는 없습니다".color)
                    return true
                }
                if (plugin.partyRegister.hasOwnParty(gSender)) {
                    val party = plugin.partyRegister.getJoined(gSender)!!
                    if (!party.banList.contains(gPlayer.player.uniqueId)) {
                        sender.sendMessage("&c플레이어가 차단되어 있지 않습니다".color)
                        return true
                    }
                    party.banList.remove(gPlayer.player.uniqueId)
                    party.joined.receiveAll(plugin.console, plugin.alert(PARTY_UNBANNED), gPlayer, party)
                } else if (plugin.partyRegister.joinedParty(gSender)) {
                    sender.sendMessage("&c파티 리더만 차단을 해제할 수 있습니다".color)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args[0].equals("accept", ignoreCase = true) && sender is Player) {
                if (args.size == 2) {
                    try {
                        plugin.partyRequestRegister.accept(sender.game, Integer.parseInt(args[1]))
                        return true
                    } catch (_: Exception) {}
                }
                sender.sendMessage("&cUsage: /p accept <code>".color)
                return true
            }
            if (args[0].equals("list", ignoreCase = true) && sender is Player) {
                val gPlayer = sender.game
                if (plugin.partyRegister.joinedParty(gPlayer)) {
                    sender.sendMessage("-------------------------------------")
                    val joined = plugin.partyRegister.getJoined(gPlayer)!!
                    sender.sendMessage(
                        "&a파티원(${joined.joined.size}명): &6".color + joined.joined,
                    )
                    sender.sendMessage("-------------------------------------")
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args[0].equals("leave", ignoreCase = true) && sender is Player) {
                val gPlayer = sender.game
                if (plugin.partyRegister.joinedParty(gPlayer)) {
                    plugin.partyRegister.getJoined(gPlayer)!!.left(gPlayer)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            if (args[0].equals("all", ignoreCase = true) && sender is Player) {
                val gPlayer = sender.game
                if (plugin.partyRegister.joinedParty(gPlayer)) {
                    plugin.partyRequestRegister.requestAll(gPlayer)
                } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
                return true
            }
            val playerExact = Bukkit.getPlayerExact(args[0])
            if (playerExact == null) { sendUsage(sender); return true }
            if (sender !is Player) { return true }
            if (sender == playerExact) {
                return true
            }
            sendPartyRequest(sender, playerExact)
            return true
        }
        partyMenu(sender as Player)
        return true
    }

    private fun sendPartyRequest(sender: Player, player: Player) {
        val gPlayer = sender.game
        val partyRegister = plugin.partyRegister
        if (partyRegister.joinedParty(gPlayer)) {
            plugin.partyRequestRegister.request(gPlayer, player.game)
        } else sender.sendMessage("&c참여 중인 파티가 없습니다".color)
    }

        private val GPlayer.openPartyMenu: Player
            get() { partyMenu(this.player); return this.player }

        fun partyMenu(player: Player) {
            val gPlayer = player.game
            player.openFrame(InvFX.frame(3, Component.text("파티 메뉴")) {
                list(1, 0, 8, 3, true, { plugin.partyRegister.toList() }) {
                    transform { p ->
                        ItemUtil.itemStack(if (p.opened) Material.CYAN_DYE else Material.PINK_DYE,
                            ChatColor.GREEN.toString() + p.name + if (p.opened) "" else "&d(비공개)",
                            ArrayList(p.joined).apply { if (!p.renamed) removeAt(0) }
                                .map { ("&7" + it + if (p.leader == it) "&7(파티장)" else "").color })
                    }
                    onClickItem { _, _, item, _ ->
                        item.first.apply {
                            if (banList.contains(gPlayer.player.uniqueId))
                                gPlayer.player.sendMessage("&c이 파티에서 차단되어 참여할 수 없습니다".color)
                            else if (opened) join(gPlayer)
                            else if (!joined.contains(gPlayer))
                                gPlayer.player.sendMessage("&c이 파티에 참여하려면 초대를 받아야 합니다".color)
                        }
                        updateParty()
                    }
                }.let { list ->
                    slot(0, 1) {
                        item =
                            if (plugin.partyRegister.size > list.size)
                                ItemUtil.itemStack(Material.FEATHER, "이전 페이지")
                            else ItemStack(Material.AIR)
                        onClick { list.index-- }
                    }
                    slot(0, 2) {
                        item =
                            if (plugin.partyRegister.size > list.size)
                                ItemUtil.itemStack(Material.FEATHER, "다음 페이지")
                            else ItemStack(Material.AIR)
                        onClick { list.index++ }
                    }
                }
                onClose { gPlayer.remove(Party.PARTY) }
                slot(0, 0) {
                    val partyRegister = plugin.partyRegister
                    val isJoinedParty = partyRegister.joinedParty(gPlayer)
                    val isOwningParty = partyRegister.hasOwnParty(gPlayer)
                    item = if (isOwningParty) ItemUtil.itemStack(Material.RED_DYE, "&c파티 해산하기")
                    else if (isJoinedParty) ItemUtil.itemStack(Material.GRAY_DYE, "&7파티 나가기")
                    else ItemUtil.itemStack(Material.LIME_DYE, "&a파티 생성하기")
                    onClick {
                        try {
                            partyRegister.createParty(gPlayer)
                            updateParty()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            })
            gPlayer.put(Party.PARTY, true)
        }

        fun updateParty() {
            plugin.playerRegister.values
                .filter { it[Party.PARTY] != null }
                .forEach { { it.openPartyMenu; Unit }.delay(plugin, 1) }
        }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String>? {
        if (sender is Player) {
            val player = sender.game
            val joinedParty = plugin.partyRegister.getJoined(player) ?: return mutableListOf("create")
            if (args.size >= 2 && listOf("kick", "ban").contains(args[0].lowercase())) {
                return ArrayList(joinedParty.joined).filter { it != player }.map { it.player.name }.toMutableList()
            }
            if (args.size >= 2 && args[0].equals("unban", ignoreCase = true)) {
                return ArrayList(joinedParty.banList)
                    .asSequence()
                    .filter { it != player.player.uniqueId }
                    .map { Bukkit.getPlayer(it) }.filterNotNull()
                    .map { it.game.player.name }
                    .toMutableList()
            }
            return Bukkit.getOnlinePlayers().filter { !joinedParty.joined.contains(it.game) }
                .map { it.name }.let { ArrayList(it).apply {
                    if (plugin.partyRegister.hasOwnParty(player))
                        addAll(listOf(
                            "visible",
                            "rename",
                            "kick",
                            "ban",
                            "unban",
                        ))
                    addAll(listOf("leave", "list"))
                } }
        }
        return null
    }

}