package io.github.bruce0203.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class GuiSlot extends GuiRegion {
    private final Consumer<InventoryClickEvent> onClick;

    public GuiSlot(int x, int y, Consumer<InventoryClickEvent> onClick) {
        super(x, y);
        this.onClick = onClick;
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        if (onClick != null) {
            onClick.accept(event);
        }
    }
}
