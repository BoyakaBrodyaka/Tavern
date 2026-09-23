package me.boyakabrodyaka.hud.menu.listener.compass;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.menu.manager.MenuManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public class CompassListener implements Listener {

    private static final String LOBBY_WORLD = "tvrn_lobby";
    private static final String COMPASS_NAME = "§aНавигация";
    private static final int COMPASS_SLOT = 4;

    private final MenuManager menuManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;
        if (!world.getName().equals(LOBBY_WORLD)) return;

        giveCompass(player);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;

        World to = event.getTo() == null ? null : event.getTo().getWorld();
        if (to == null) return;

        Player player = event.getPlayer();

        if (to.getName().equals(LOBBY_WORLD)) {
            giveCompass(player);
            return;
        }

        removeCompass(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;
        if (!world.getName().equals(LOBBY_WORLD)) return;

        ItemStack item = event.getItem();
        if (!isCompass(item)) return;
        if (!event.getAction().toString().contains("RIGHT")) return;

        event.setCancelled(true);
        this.menuManager.openMainMenu(player);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (!isCompass(item)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world == null) return;
        if (!world.getName().equals(LOBBY_WORLD)) return;

        ItemStack item = event.getItem().getItemStack();
        if (!isCompass(item)) return;

        event.setCancelled(true);
    }

    private void giveCompass(Player player) {
        if (hasCompass(player)) return;

        PlayerInventory inventory = player.getInventory();
        inventory.setItem(COMPASS_SLOT, createCompass());
        player.updateInventory();
    }

    private void removeCompass(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (!isCompass(item)) continue;
            inventory.remove(item);
        }

        player.updateInventory();
    }

    private boolean hasCompass(Player player) {
        for (ItemStack item : player.getInventory().getContents()) if (isCompass(item)) return true;

        return false;
    }

    private boolean isCompass(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.COMPASS) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;

        return meta.getDisplayName().equals(COMPASS_NAME);
    }

    private ItemStack createCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(COMPASS_NAME);
        compass.setItemMeta(meta);
        return compass;
    }
}