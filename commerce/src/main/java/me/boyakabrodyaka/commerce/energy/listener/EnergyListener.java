package me.boyakabrodyaka.commerce.energy.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.energy.EnergyManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class EnergyListener implements Listener {

    private final EnergyManager energyManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFoodChange(FoodLevelChangeEvent event) { event.setCancelled(true); }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        World world = event.getPlayer().getWorld();
        if (world == null) return;
        this.energyManager.remove(world.getName());
    }
}