package io.github.bruce0203.gui;

import io.github.bruce0203.gui.utils.Function2;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface GuiFrame {

    void openInventory(Player player);

    void slot(int x, int y, Consumer<InventoryClickEvent> onClick);
    void slot(int x, int y, ItemStack itemStack, Consumer<InventoryClickEvent> onClick);

    <T> void list(int x, int y, int width, int height, Supplier<List<T>> items,
                         Function<T, ItemStack> transform, Function2<GuiList<T>, GuiFrame> init);

    void item(int x, int y, ItemStack itemStack);

    ItemStack item(int x, int y);

}
