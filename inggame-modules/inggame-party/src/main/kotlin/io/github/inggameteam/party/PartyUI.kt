package io.github.inggameteam.party

import io.github.brucefreedy.mccommand.MCCommand
import io.github.brucefreedy.mccommand.api.context.CommandContext
import io.github.brucefreedy.mccommand.api.tree.ArgumentCommandNode
import io.github.brucefreedy.mccommand.player
import io.github.inggameteam.alert.Alert
import io.github.inggameteam.party.PartyAlert.JOINED_PARTY_NOT_EXIST
import io.github.inggameteam.player.GPlayer
import io.github.inggameteam.player.eq
import io.github.inggameteam.scheduler.delay
import io.github.inggameteam.utils.ColorUtil.color
import io.github.inggameteam.utils.ItemUtil
import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class PartyUI(val plugin: PartyPlugin) {
    private fun sendUsage(sender: CommandSender) = sender.sendMessage("&cUsage: /p [player]".color())
    val Player.game get() = plugin[this]
    val GPlayer.isJoinedParty get() = plugin.partyRegister.joinedParty(this)
    val GPlayer.partyOrNull get() = plugin.partyRegister.getJoined(this)
    val GPlayer.party get() = plugin.partyRegister.getJoined(this)!!
    fun CommandContext<CommandSender>.party(sendMsg: Boolean = true, init: Party.(GPlayer) -> Unit) {
        val gPlayer = player.game
        gPlayer.partyOrNull?.apply { this.init(gPlayer) }
            ?: if (sendMsg) plugin.component.send(JOINED_PARTY_NOT_EXIST, gPlayer)
    }

    fun CommandContext<CommandSender>.args(index: Int = 1, init: (GPlayer) -> Unit) {
        Bukkit.getPlayerExact(args[1])?.game?.apply { init(this) }
            ?: plugin.component.send(Alert.NO_PLAYER_EXIST, player.game)
    }

    val ArgumentCommandNode<CommandSender>.memberTab
        get() =
            tab {
                player.game.partyOrNull?.run { joined.filterNot { it == leader }.map { it.name }.toList() } ?: listOf()
            }

    init {
        MCCommand(plugin as JavaPlugin) {
            command("p", "party") {
                executeThen("create") { plugin.partyRegister.createParty(player.game) }
                executeThen("rename") { party { rename(it, argument.subList(2, argument.size).joinToString(" ")) } }
                executeThen("visible") { party { visible(it) } }
                then("promote") {
                    memberTab
                    execute { args { arg -> party { promote(it, arg) } } }
                }
                then("kick") {
                    memberTab
                    execute { args { arg -> party { kick(it, arg) } } }
                }
                then("ban") {
                    memberTab
                    execute { args { arg -> party { ban(it, arg) } } }
                }
                then("unban") {
                    tab {
                        player.game.partyOrNull?.run {
                            banList.filterNot { it == leader.uniqueId }
                                .mapNotNull { Bukkit.getPlayer(it)?.name }.toList()
                        }
                            ?: listOf()
                    }
                    execute { args { arg -> party { unban(it, arg) } } }
                }
                executeThen("list") { party { listMembers(it) } }
                executeThen("all") { plugin.partyRequestRegister.inviteAll(player.game) }
                execute { args(0) { plugin.partyRequestRegister.invitePlayer(player.game, it) } }
                tab {
                    val gamePlayer = player.game
                    if (gamePlayer.isJoinedParty) {
                        val party = gamePlayer.party
                        if (party.leader eq gamePlayer) {
                            return@tab arrayListOf(*defTab).apply { removeAll(arrayOf("create")) }
                        } else {
                            return@tab arrayListOf(*defTab).apply {
                                removeAll(arrayOf("unban",
                                    "ban",
                                    "kick",
                                    "promote",
                                    "visible",
                                    "rename",
                                    "create"))
                            }
                        }
                    } else {
                        return@tab arrayListOf("create")
                    }
                }
            }
        }
    }

    private val GPlayer.openPartyMenu: Player
        get() { partyMenu(this); return this }

    fun partyMenu(player: Player) {
        val gPlayer = plugin[player]
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
                        if (banList.contains(gPlayer.uniqueId))
                            gPlayer.sendMessage("&c이 파티에서 차단되어 참여할 수 없습니다".color)
                        else if (opened) join(gPlayer)
                        else if (!joined.contains(gPlayer))
                            gPlayer.sendMessage("&c이 파티에 참여하려면 초대를 받아야 합니다".color)
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
}