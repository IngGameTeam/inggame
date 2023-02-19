package io.github.inggameteam.inggame.plugman;

import io.github.inggameteam.inggame.plugman.util.BukkitCommandWrap;
import io.github.inggameteam.inggame.plugman.util.BukkitCommandWrap_Useless;

public class PlugMan {

    private static BukkitCommandWrap bukkitCommandWrap = null;

    public static BukkitCommandWrap getBukkitCommandWrap() {
        if (bukkitCommandWrap == null) {
            try {
                Class.forName("com.mojang.brigadier.CommandDispatcher");
                bukkitCommandWrap = new BukkitCommandWrap();
            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                bukkitCommandWrap = new BukkitCommandWrap_Useless();
            }
        }
        return bukkitCommandWrap;
    }
}
