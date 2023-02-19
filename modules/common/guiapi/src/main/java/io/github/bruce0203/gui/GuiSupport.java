package io.github.bruce0203.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class GuiSupport implements Listener {

    private static final HashMap<Plugin, GuiSupport> guiSupportMap = new HashMap<>();

    public static void addSupport(Plugin plugin) {
        if (!plugin.isEnabled()) throw new AssertionError("plugin is not enabled");
        var guiSupport = new GuiSupport();
        plugin.getServer().getPluginManager().registerEvents(guiSupport, plugin);
        guiSupportMap.put(plugin, guiSupport);
    }

    @SuppressWarnings("unused")
    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        var plugin = event.getPlugin();
        if (guiSupportMap.remove(plugin) != this) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof GuiWindow) {
                player.closeInventory();
            }
        });
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onOpen(InventoryOpenEvent event) {
        var window = window(event.getInventory());
        if (window == null) return;
        window.onOpen(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        var window = window(event.getInventory());
        if (window == null) return;
        window.onClose(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        GuiWindow window = window(inventory);
        if (window == null) return;
        int slot = event.getRawSlot();
        if (slot < 0) {
            window.onClickOutside(event);
        } else if (slot < inventory.getSize()) {
            window.onClick(event);
        } else {
            window.onClickBottom(event);
        }
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrag(InventoryDragEvent event) {
        var window = window(event.getInventory());
        if (window == null) return;
        window.onDrag(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        var entity = event.getEntity();
        if (!(entity instanceof Player player)) return;
        var window = window(player.getOpenInventory().getTopInventory());
        if (window == null) return;
        window.onPickupItem(event);
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent event) {
        var player = event.getPlayer();
        var window = window(player.getOpenInventory().getTopInventory());
        if (window == null) return;
        window.onDropItem(event);
    }

    public GuiWindow window(Inventory inventory) {
        return inventory.getHolder() instanceof GuiWindow window ? window : null;
    }

}
