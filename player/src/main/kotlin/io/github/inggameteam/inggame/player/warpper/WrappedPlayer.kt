package io.github.inggameteam.inggame.player.warpper

import io.github.inggameteam.inggame.component.delegate.Delegate
import io.github.inggameteam.inggame.component.model.AlertReciver
import io.github.inggameteam.inggame.player.bukkit.NotImplementedPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

open class WrappedPlayer(delegate: Delegate)
    : Delegate by delegate, AlertReciver,
    Player by Bukkit.getPlayer(delegate.nameSpace as UUID) ?: NotImplementedPlayer()
{
    override fun toString() = name
}