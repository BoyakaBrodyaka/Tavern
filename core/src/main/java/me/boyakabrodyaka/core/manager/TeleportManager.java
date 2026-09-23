package me.boyakabrodyaka.core.manager;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.core.util.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class TeleportManager {

    private static final String LOBBY_WORLD = "tvrn_lobby";
    private static final double SPAWN_OFFSET = 0.5D;
    private static final String TELEPORT_ERROR = "&cОшибка телепортации";

    private final WorldManager worldManager;
    private final ConcurrentHashMap<String, String> playerWorlds = new ConcurrentHashMap<>();

    public boolean teleportToWorld(Player player, String worldName) {
        World targetWorld = this.worldManager.getWorld(worldName);
        if (targetWorld == null) return false;

        Location spawn = targetWorld.getSpawnLocation().clone();
        spawn.add(SPAWN_OFFSET, 0, SPAWN_OFFSET);

        if (!player.teleport(spawn)) {
            player.sendMessage(Color.color(TELEPORT_ERROR));
            return false;
        }

        this.playerWorlds.put(player.getName(), worldName);
        return true;
    }

    public boolean teleportToLobby(Player player) { return teleportToWorld(player, LOBBY_WORLD); }

    public String getLastWorld(Player player) {
        return this.playerWorlds.get(player.getName());
    }

    public void setLastWorld(Player player, String worldName) {
        this.playerWorlds.put(player.getName(), worldName);
    }

    public void removePlayer(Player player) {
        this.playerWorlds.remove(player.getName());
    }

    public void clear() {
        this.playerWorlds.clear();
    }
}