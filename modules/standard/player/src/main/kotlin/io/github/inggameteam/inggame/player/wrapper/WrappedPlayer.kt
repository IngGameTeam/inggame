package io.github.inggameteam.inggame.player.wrapper

import io.github.inggameteam.inggame.component.model.AlertReciver
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import io.github.inggameteam.inggame.utils.SafeSetWithToString
import io.github.inggameteam.inggame.utils.TagContainer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

interface WrappedPlayer : Wrapper, AlertReciver, Player, TagContainer

open class WrappedPlayerImp(wrapper: Wrapper)
    : SimpleWrapper(wrapper), AlertReciver, WrappedPlayer,
    Player by Bukkit.getPlayer(wrapper.nameSpace as UUID)
        ?: throw AssertionError("player is offline")
{
    override var tags: SafeSetWithToString<String> by default { SafeSetWithToString<String>() }
    override fun toString() = name
}