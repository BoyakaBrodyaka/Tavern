package me.boyakabrodyaka.commerce.day.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.finisher.DayFinisher;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class DayListener implements Listener {

    private final DayFinisher dayFinisher;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) { this.dayFinisher.cancel(event.getPlayer()); }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        World from = event.getFrom().getWorld();
        World to = event.getTo() == null ? null : event.getTo().getWorld();

        if (from == null || to == null) return;
        if (from.equals(to)) return;

        Player player = event.getPlayer();
        this.dayFinisher.cancel(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) { this.dayFinisher.cancel(event.getPlayer()); }
}