package me.boyakabrodyaka.hud.hologram.table;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
public class TableHologram {

    private final String world;
    private final double x;
    private final double y;
    private final double z;

    public TableHologram(World world) {
        this.world = world.getName();
        this.x = TableHologramCoordinate.X.getValue();
        this.y = TableHologramCoordinate.Y.getValue();
        this.z = TableHologramCoordinate.Z.getValue();
    }

    public Location toLocation() {
        World bukkitWorld = Bukkit.getWorld(this.world);
        if (bukkitWorld == null) return null;

        return new Location(bukkitWorld, this.x, this.y, this.z);
    }
}