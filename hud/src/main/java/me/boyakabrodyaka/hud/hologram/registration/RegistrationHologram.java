package me.boyakabrodyaka.hud.hologram.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@RequiredArgsConstructor
public class RegistrationHologram {

    private final String world;
    private final double x;
    private final double y;
    private final double z;

    public RegistrationHologram(World world) {
        this.world = world.getName();
        this.x = RegistrationHologramCoordinate.X.getValue();
        this.y = RegistrationHologramCoordinate.Y.getValue();
        this.z = RegistrationHologramCoordinate.Z.getValue();
    }

    public Location toLocation() {
        World bukkitWorld = Bukkit.getWorld(this.world);
        if (bukkitWorld == null) return null;

        return new Location(bukkitWorld, this.x, this.y, this.z);
    }
}