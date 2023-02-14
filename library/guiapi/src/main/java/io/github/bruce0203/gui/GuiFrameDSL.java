package io.github.bruce0203.gui;

import io.github.bruce0203.gui.utils.Function2;
import io.github.bruce0203.gui.utils.Function3;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GuiFrameDSL {

    private final GuiFrameImpl guiFrame;
    GuiFrameDSL(GuiFrameImpl guiFrame) { this.guiFrame = guiFrame; }

    public void openInventory(Player player) {
        guiFrame.openInventory(player);
    }

    public GuiFrameDSL slot(int x, int y, Consumer<InventoryClickEvent> onClick) {
        guiFrame.slot(x, y, onClick);
        return this;
    }

    public GuiFrameDSL slot(int x, int y, ItemStack itemStack, Consumer<InventoryClickEvent> onClick) {
        guiFrame.slot(x, y, itemStack, onClick);
        return this;
    }

    public <T> GuiFrameDSL list(int x, int y, int width, int height, Supplier<List<T>> items,
                                Function<T, ItemStack> transform, Function2<GuiListDSL<T>, GuiFrameDSL> init) {
        guiFrame.list(x, y, width, height, items, transform,
                (guiList, guiFrame) -> init.invoke(new GuiListDSL<>(guiList), this));
        return this;
    }

    public <T> GuiFrameDSL list(int x, int y, int width, int height, Supplier<List<T>> items,
                                Function<T, ItemStack> transform) {
        guiFrame.list(x, y, width, height, items, transform, (list, gui) -> {});
        return this;
    }

    public GuiFrameDSL item(int x, int y, ItemStack itemStack) {
        guiFrame.item(x, y, itemStack);
        return this;
    }

    public GuiFrameDSL onClick(Function3<Integer, Integer, InventoryClickEvent> onClick) {
        guiFrame.onClick = onClick;
        return this;
    }

    public GuiFrameDSL onOpen(Consumer<InventoryOpenEvent> onOpen) {
        guiFrame.onOpen = onOpen;
        return this;
    }

    public GuiFrameDSL onClose(Consumer<InventoryCloseEvent> onClose) {
        guiFrame.onClose = onClose;
        return this;
    }

    public GuiFrameDSL onClickBottom(Consumer<InventoryClickEvent> onClickBottom) {
        guiFrame.onClickBottom = onClickBottom;
        return this;
    }

    public GuiFrameDSL onClickOutside(Consumer<InventoryClickEvent> onClickOutside) {
        guiFrame.onClickOutside = onClickOutside;
        return this;
    }

}
