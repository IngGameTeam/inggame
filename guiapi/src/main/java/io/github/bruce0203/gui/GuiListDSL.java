package io.github.bruce0203.gui;

import io.github.bruce0203.gui.utils.Pair;
import io.github.bruce0203.gui.utils.Function4;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListDSL<T> {

    private final GuiList<T> guiList;


    public GuiListDSL(GuiList<T> guiList) {
        this.guiList = guiList;
    }

    public GuiListDSL<T> setIndex(int index) {
        guiList.setIndex(index);
        return this;
    }

    public int getIndex() {
        return guiList.getIndex();
    }

    public GuiListDSL<T> onClick(Function4<Integer, Integer, Pair<ItemStack, T>, InventoryClickEvent> onClick) {
        guiList.onClick(onClick);
        return this;
    }


}
