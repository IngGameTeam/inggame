package io.github.inggameteam.inggame.utils

import org.bukkit.entity.Player

fun Player.die() {
    damage(health)
}