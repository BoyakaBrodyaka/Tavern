package me.boyakabrodyaka.operation.registration.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.registration.RegistrationManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class RegistrationListener implements Listener {

    private final RegistrationManager manager;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if (!hasMovedBlock(event.getFrom(), event.getTo())) return;

        this.manager.update(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        this.manager.update(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.manager.remove(event.getPlayer());
    }

    private boolean hasMovedBlock(Location from, Location to) {
        if (to == null) return false;

        return from.getBlockX() != to.getBlockX()
                || from.getBlockY() != to.getBlockY()
                || from.getBlockZ() != to.getBlockZ();
    }
}