package io.github.bruce0203.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class GuiRegion {

    public int x;
    public int y;

    public GuiRegion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void onClick(int x, int y, InventoryClickEvent event);

}
