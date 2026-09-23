package me.boyakabrodyaka.cuisine.kitchen.station;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@RequiredArgsConstructor
public class KitchenStation {

    private final String key;
    private final double x;
    private final double y;
    private final double z;

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public boolean matches(Location location) {
        if (location == null) return false;
        if (location.getWorld() == null) return false;

        return location.getBlockX() == (int) Math.floor(this.x)
                && location.getBlockY() == (int) Math.floor(this.y)
                && location.getBlockZ() == (int) Math.floor(this.z);
    }
}