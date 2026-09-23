package me.boyakabrodyaka.core.listener.player;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.core.manager.TeleportManager;
import me.boyakabrodyaka.core.manager.WorldManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final TeleportManager teleportManager;
    private final WorldManager worldManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String lastWorld = this.teleportManager.getLastWorld(player);

        if (lastWorld != null && this.worldManager.worldExists(lastWorld)) {
            this.teleportManager.teleportToWorld(player, lastWorld);
            return;
        }

        this.teleportManager.teleportToLobby(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world == null) return;
        if (!this.worldManager.worldExists(world.getName())) return;

        this.teleportManager.setLastWorld(player, world.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;

        World toWorld = event.getTo() == null ? null : event.getTo().getWorld();
        if (toWorld == null) return;
        if (!this.worldManager.worldExists(toWorld.getName())) return;

        this.teleportManager.setLastWorld(event.getPlayer(), toWorld.getName());
    }
}