package me.boyakabrodyaka.operation.dining.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.operation.dining.DiningManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class DiningListener implements Listener {

    private final DiningManager manager;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        this.load(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        this.load(event.getPlayer());
    }

    private void load(Player player) {
        World world = player.getWorld();
        if (world == null) return;

        this.manager.load(world);
    }
}