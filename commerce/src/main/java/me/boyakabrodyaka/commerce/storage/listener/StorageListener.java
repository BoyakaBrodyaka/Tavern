package me.boyakabrodyaka.commerce.storage.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import me.boyakabrodyaka.commerce.storage.StorageLoader;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class StorageListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";

    private final StorageLoader storageLoader;
    private final AccountManager accountManager;
    private final EnergyManager energyManager;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        String fromWorld = event.getFrom().getName();
        World toWorld = event.getPlayer().getWorld();

        this.transfer(fromWorld, toWorld == null ? null : toWorld.getName());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        World from = event.getFrom().getWorld();
        World to = event.getTo() == null ? null : event.getTo().getWorld();

        if (from == null || to == null) return;
        if (from.equals(to)) return;

        this.transfer(from.getName(), to.getName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world == null) return;
        if (!world.getName().contains(WORLD_SUFFIX)) return;

        this.storageLoader.save(world.getName(), this.accountManager, this.energyManager);
    }

    private void transfer(String fromWorld, String toWorld) {
        if (fromWorld != null && fromWorld.contains(WORLD_SUFFIX)) {
            this.storageLoader.save(fromWorld, this.accountManager, this.energyManager);
        }

        if (toWorld != null && toWorld.contains(WORLD_SUFFIX)) {
            this.storageLoader.load(toWorld, this.accountManager, this.energyManager);
        }
    }
}