package me.boyakabrodyaka.operation.registration;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RegistrationManager {

    private final RegistrationRegistry registry;
    private final RegistrationZone zone;

    public RegistrationManager() {
        this.registry = new RegistrationRegistry();
        this.zone = new RegistrationZone();
    }

    public void update(Player player) {
        World world = player.getWorld();
        if (world == null) return;

        Location location = player.getLocation();

        if (!RegistrationWorld.MAP.matches(world.getName())) {
            if (this.registry.isInside(player)) this.registry.setInside(player, false);
            return;
        }

        boolean inside = this.zone.contains(location.getX(), location.getY(), location.getZ());
        boolean wasInside = this.registry.isInside(player);

        if (inside == wasInside) return;

        this.registry.setInside(player, inside);
    }

    public boolean isPlayerInZone(Player player) {
        World world = player.getWorld();
        if (world == null) return false;
        if (!RegistrationWorld.MAP.matches(world.getName())) return false;

        Location location = player.getLocation();

        return this.zone.contains(location.getX(), location.getY(), location.getZ());
    }

    public void remove(Player player) {
        this.registry.remove(player);
    }

    public void clear() {
        this.registry.clear();
    }

    public RegistrationZone getZone() {
        return this.zone;
    }
}