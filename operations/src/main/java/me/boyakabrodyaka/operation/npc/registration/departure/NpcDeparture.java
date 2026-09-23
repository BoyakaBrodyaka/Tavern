package me.boyakabrodyaka.operation.npc.registration.departure;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class NpcDeparture {

    private final String world;

    public NpcDeparture(World world) {
        this.world = world.getName();
    }

    public Location toLocation() {
        World bukkitWorld = Bukkit.getWorld(this.world);
        if (bukkitWorld == null) return null;

        return new Location(
                bukkitWorld,
                NpcDepartureCoordinate.X.getValue(),
                NpcDepartureCoordinate.Y.getValue(),
                NpcDepartureCoordinate.Z.getValue()
        );
    }

    public Location disappearLocation(World world) {
        return new Location(
                world,
                NpcDepartureCoordinate.DISAPPEAR_X.getValue(),
                NpcDepartureCoordinate.DISAPPEAR_Y.getValue(),
                NpcDepartureCoordinate.DISAPPEAR_Z.getValue()
        );
    }
}