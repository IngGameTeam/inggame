package io.github.bruce0203.gui;

import org.bukkit.plugin.Plugin;

public class Gui {

    public static GuiFrameDSL frame(Plugin plugin, int lines, String title) {
        GuiSupport.addSupport(plugin);
        GuiFrameImpl guiFrame = new GuiFrameImpl(lines, title);
        return new GuiFrameDSL(guiFrame);
    }


}
