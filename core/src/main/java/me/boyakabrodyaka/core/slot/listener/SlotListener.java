package me.boyakabrodyaka.core.slot.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.core.slot.SlotManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class SlotListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";

    private final SlotManager manager;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!isPlayerWorld(player)) return;
        this.manager.applyLockedAll(player);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        World toWorld = event.getTo() == null ? null : event.getTo().getWorld();
        if (toWorld == null) return;

        Player player = event.getPlayer();

        if (toWorld.getName().contains(WORLD_SUFFIX)) {
            this.manager.applyLockedAll(player);
            return;
        }
        this.manager.clearLocked(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (!this.manager.isBarrier(item)) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickup(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (!this.manager.isBarrier(item)) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (!isPlayerWorld(player)) return;

        if (this.manager.isBarrier(event.getCurrentItem())) {
            event.setCancelled(true);
            return;
        }

        if (this.manager.isBarrier(event.getCursor())) {
            event.setCancelled(true);
            return;
        }

        int rawSlot = event.getRawSlot();

        if (this.manager.isHotbar(rawSlot)) {
            if (this.manager.isAllowed(rawSlot)) return;

            event.setCancelled(true);
            return;
        }

        if (this.manager.isInventory(rawSlot)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (!isPlayerWorld(player)) return;

        for (int slot : event.getRawSlots()) {
            if (this.manager.isHotbar(slot) && !this.manager.isAllowed(slot)) {
                event.setCancelled(true);
                return;
            }
            if (this.manager.isInventory(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    private boolean isPlayerWorld(Player player) {
        World world = player.getWorld();
        if (world == null) return false;
        return world.getName().contains(WORLD_SUFFIX);
    }
}