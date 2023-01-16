package io.github.bruce0203.gui;

import io.github.bruce0203.gui.utils.Function2;
import io.github.bruce0203.gui.utils.Function3;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GuiFrameImpl implements GuiWindow, GuiFrame {

    private final ArrayList<GuiRegion> slots = new ArrayList<>();

    private final Inventory inv;
    @Override
    public Inventory getInventory() { return inv; }

    public Consumer<InventoryOpenEvent> onOpen;
    public Consumer<InventoryCloseEvent> onClose;
    public Function3<Integer, Integer, InventoryClickEvent> onClick;
    public Consumer<InventoryClickEvent> onClickBottom;
    public Consumer<InventoryClickEvent> onClickOutside;

    public GuiFrameImpl(int lines, String title) {
        inv = Bukkit.createInventory(this, lines * 9, title);
    }

    private void assertItemSlot(int x, int y) {
        var lines = inv.getSize() / 9;
        if (0 > x || x >= 9 || 0 > y || y >= lines) {
            throw new AssertionError("require 0 <= x <= 8 0 <= y < " + lines);
        }

    }

    @Override
    public void openInventory(Player player) {
        player.openInventory(getInventory());
    }

    @Override
    public void slot(int x, int y, Consumer<InventoryClickEvent> onClick) {
        assertItemSlot(x, y);
        var guiSlot = new GuiSlot(x, y, onClick);
        slots.add(guiSlot);
    }

    @Override
    public void slot(int x, int y, ItemStack itemStack, Consumer<InventoryClickEvent> onClick) {
        assertItemSlot(x, y);
        var guiSlot = new GuiSlot(x, y, onClick);
        item(x, y, itemStack);
        slots.add(guiSlot);
    }

    @Override
    public <T> void list(int x, int y, int width, int height, Supplier<List<T>> items,
                                Function<T, ItemStack> transform, Function2<GuiList<T>, GuiFrame> init) {
        GuiList<T> guiList = new GuiList<>(x, y, width, height, items, transform, this);
        init.invoke(guiList, this);
        slots.add(guiList);
        guiList.update();
    }

    @Override
    public void item(int x, int y, ItemStack itemStack) {
        assertItemSlot(x, y);
        inv.setItem(x + y * 9, itemStack);
    }

    @Override
    public ItemStack item(int x, int y) {
        assertItemSlot(x, y);
        return inv.getItem(x + y * 9);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        if (onOpen == null) return;
        this.onOpen.accept(event);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        if (onClose == null) return;
        this.onClose.accept(event);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        var slot = event.getSlot();
        var x = slot % 9;
        var y = slot / 9;

        if (onClick != null) {
            this.onClick.invoke(x, y, event);
        }

        slots.stream().filter(s -> isEntered(s, x, y)).forEach(s -> s.onClick(x, y, event));
    }

    private boolean isEntered(GuiRegion s, int x, int y) {
        if (s.x == x && s.y == y) return true;
        else return s instanceof GuiList<?> l && l.x <= x && x <= l.width && l.y <= y && y <= l.height;
    }

    @Override
    public void onClickBottom(InventoryClickEvent event) {
        event.setCancelled(true);
        if (onClickBottom == null) return;
        onClickBottom.accept(event);
    }

    @Override
    public void onClickOutside(InventoryClickEvent event) {
        event.setCancelled(true);
        if (onClickOutside == null) return;
        onClickOutside.accept(event);
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPickupItem(EntityPickupItemEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }


}
