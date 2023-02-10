package io.github.bruce0203.gui;

import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryHolder;

public interface GuiWindow extends InventoryHolder {

    void onOpen(InventoryOpenEvent event);

    void onClose(InventoryCloseEvent event);

    void onClick(InventoryClickEvent event);

    void onClickBottom(InventoryClickEvent event);

    void onClickOutside(InventoryClickEvent event);

    void onDrag(InventoryDragEvent event);

    void onPickupItem(EntityPickupItemEvent event);

    void onDropItem(PlayerDropItemEvent event);


}
