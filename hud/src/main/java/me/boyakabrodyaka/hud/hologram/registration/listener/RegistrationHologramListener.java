package me.boyakabrodyaka.hud.hologram.registration.listener;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.hud.hologram.registration.RegistrationHologramManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class RegistrationHologramListener implements Listener {

    private static final String WORLD_SUFFIX = "_map";

    private final RegistrationHologramManager manager;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!isPlayerWorld(world)) return;
        if (world.getPlayers().size() > 1) return;

        this.manager.spawn(world);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        World from = event.getFrom();
        World to = event.getPlayer().getWorld();

        if (isPlayerWorld(from) && from.getPlayers().isEmpty()) this.manager.remove(from);
        if (isPlayerWorld(to)) this.manager.spawn(to);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!isPlayerWorld(world)) return;
        if (world.getPlayers().size() > 1) return;

        this.manager.remove(world);
    }

    private boolean isPlayerWorld(World world) {
        if (world == null) return false;
        return world.getName().contains(WORLD_SUFFIX);
    }
}