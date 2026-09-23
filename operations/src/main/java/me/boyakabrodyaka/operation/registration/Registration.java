package me.boyakabrodyaka.operation.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@RequiredArgsConstructor
public class Registration {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public Location toLocation() {
        World bukkitWorld = Bukkit.getWorld(this.world);
        if (bukkitWorld == null) return null;

        return new Location(bukkitWorld, this.x, this.y, this.z, this.yaw, this.pitch);
    }
}