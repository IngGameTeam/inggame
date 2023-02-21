package io.github.inggameteam.inggame.player.warpper

import io.github.inggameteam.inggame.component.model.AlertReciver
import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper
import io.github.inggameteam.inggame.component.wrapper.Wrapper
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

open class WrappedPlayer(wrapper: Wrapper)
    : SimpleWrapper(wrapper), AlertReciver,
    Player by Bukkit.getPlayer(wrapper.nameSpace as UUID)
        ?: throw AssertionError("player is offline")
{
    override fun toString() = name
}