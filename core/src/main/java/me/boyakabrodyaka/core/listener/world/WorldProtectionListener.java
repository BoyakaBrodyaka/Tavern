package me.boyakabrodyaka.core.listener.world;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class WorldProtectionListener implements Listener {

    private static final int MAX_HEALTH = 20;
    private static final int MAX_FOOD = 20;
    private static final float MAX_SATURATION = 10.0F;
    private static final float ZERO_FALL_DISTANCE = 0.0F;
    private static final int ZERO_FIRE_TICKS = 0;

    private final String lobbyWorld;
    private final String adminName;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!isProtected(player)) return;
        applyLobbyMode(player);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        World toWorld = event.getTo() == null ? null : event.getTo().getWorld();
        if (toWorld == null) return;
        if (!toWorld.getName().equals(this.lobbyWorld)) return;

        Player player = event.getPlayer();
        if (!isProtected(player)) return;

        applyLobbyMode(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) { if (isProtected(event.getPlayer())) event.setCancelled(true); }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) { if (isProtected(event.getPlayer())) event.setCancelled(true); }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!isProtected((Player) event.getEntity())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (!isProtected(player)) return;

        event.setCancelled(true);
        player.setFoodLevel(MAX_FOOD);
        player.setSaturation(MAX_SATURATION);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) { if (isProtected(event.getPlayer())) event.setCancelled(true); }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) { if (isProtected(event.getPlayer())) event.setCancelled(true); }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!isProtected((Player) event.getWhoClicked())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) { if (isProtected(event.getPlayer())) event.setCancelled(true); }

    private boolean isProtected(Player player) { return isLobby(player) && !isAdmin(player); }

    private boolean isLobby(Player player) {
        if (player == null) return false;
        World world = player.getWorld();
        if (world == null) return false;
        return world.getName().equals(this.lobbyWorld);
    }

    private boolean isAdmin(Player player) { return player != null && player.getName().equalsIgnoreCase(this.adminName); }

    private void applyLobbyMode(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.setHealth(MAX_HEALTH);
        player.setFoodLevel(MAX_FOOD);
        player.setSaturation(MAX_SATURATION);
        player.setFallDistance(ZERO_FALL_DISTANCE);
        player.setFireTicks(ZERO_FIRE_TICKS);
    }
}