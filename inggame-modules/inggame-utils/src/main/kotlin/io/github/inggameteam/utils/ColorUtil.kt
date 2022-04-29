package io.github.inggameteam.utils

import org.bukkit.ChatColor
import org.bukkit.Color

object ColorUtil {
    @JvmStatic
    fun String.color(): String = ChatColor.translateAlternateColorCodes('&', this)
    @JvmStatic
    val String.color get(): String = ChatColor.translateAlternateColorCodes('&', this)


    @JvmStatic
    fun getRGB(hex: Int): Color {
        val r = hex and 0xFF0000 shr 16
        val g = hex and 0xFF00 shr 8
        val b = hex and 0xFF
        return Color.fromRGB(r, g, b)
    }
    @JvmStatic
    fun hex2Rgb(colorStr: String): Color {
        var s = colorStr
        if (!s.startsWith("#")) s = "#" + colorStr
        return Color.fromRGB(
            Integer.valueOf( s.substring( 1, 3 ), 16 ),
            Integer.valueOf( s.substring( 3, 5 ), 16 ),
            Integer.valueOf( s.substring( 5, 7 ), 16 )
        )
    }
}