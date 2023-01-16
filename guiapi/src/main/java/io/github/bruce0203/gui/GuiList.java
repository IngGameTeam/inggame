package io.github.bruce0203.gui;

import io.github.bruce0203.gui.utils.Function4;
import io.github.bruce0203.gui.utils.Pair;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class GuiList<T> extends GuiRegion {

    private final GuiWindow guiWindow;
    public int width;
    public int height;
    private int index = 0;
    private final Supplier<List<T>> items;
    private final Function<T, ItemStack> transform;
    public Function4<Integer, Integer, Pair<ItemStack, T>, InventoryClickEvent> onClick;

    public List<Pair<ItemStack, T>> display;

    public int getIndex() {
        return index;
    }

    public void setIndex(int input) {
        this.index = input;
        update();
    }

    public void update() {
        List<T> list = items.get();
        display = new ArrayList<>();
        int size = list.size();
        if (size == 0) return;
        AtomicInteger index = new AtomicInteger(this.index);
        IntStream.range(y, y + height).forEach(y -> IntStream.range(x, x + width).forEach(x -> {
                int i = x + y * 9;
                ItemStack item = null;
                int ind = index.getAndAdd(1);
                if (ind < 0) {
                    ind = size - (Math.abs(ind) % size);
                } else {
                    ind = ind % size;
                }
                if (ind <= (x * y < size ? size : size + this.index)) {
                    T context = list.get(ind % size);
                    item = transform.apply(context);
                    display.add(new Pair<>(item, context));
                }
                guiWindow.getInventory().setItem(i, item);
        }));
    }

    public GuiList(int x, int y, int width, int height, Supplier<List<T>> items, Function<T, ItemStack> transform, GuiWindow guiWindow) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.items = items;
        this.transform = transform;
        this.guiWindow = guiWindow;
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        onClick.invoke(x, y, display.get(x + y * 9), event);
        update();
    }

    public void onClick(Function4<Integer, Integer, Pair<ItemStack, T>, InventoryClickEvent> onClick) {
        this.onClick = onClick;
    }
}
