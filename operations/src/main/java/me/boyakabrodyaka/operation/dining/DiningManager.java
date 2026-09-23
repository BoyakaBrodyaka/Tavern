package me.boyakabrodyaka.operation.dining;

import me.boyakabrodyaka.operation.dining.chair.DiningChair;
import me.boyakabrodyaka.operation.dining.chair.DiningChairCoordinate;
import me.boyakabrodyaka.operation.dining.chair.DiningChairFactory;
import me.boyakabrodyaka.operation.dining.chair.DiningChairIndex;
import me.boyakabrodyaka.operation.dining.chair.DiningChairRegistry;
import org.bukkit.Location;
import org.bukkit.World;

public class DiningManager {

    private final DiningChairFactory factory;
    private final DiningChairRegistry registry;
    private final DiningZone zone;

    public DiningManager() {
        this.factory = new DiningChairFactory();
        this.registry = new DiningChairRegistry();
        this.zone = new DiningZone();
    }

    public void load(World world) {
        if (world == null) return;
        if (!DiningWorld.MAP.matches(world.getName())) return;
        if (this.registry.size() > 0) return;

        register(world, DiningChairIndex.FIRST);
        register(world, DiningChairIndex.SECOND);
        register(world, DiningChairIndex.THIRD);
        register(world, DiningChairIndex.FOURTH);
    }

    private void register(World world, DiningChairIndex index) {
        int seat = index.getIndex();

        double x = coordinate(index, Axis.X);
        double y = coordinate(index, Axis.Y);
        double z = coordinate(index, Axis.Z);

        DiningChair chair = this.factory.create(world, seat, x, y, z);
        this.registry.register(chair);
    }

    private double coordinate(DiningChairIndex index, Axis axis) {
        String name = "CHAIR_" + index.getIndex() + "_" + axis.name();
        return DiningChairCoordinate.valueOf(name).getValue();
    }

    private enum Axis {
        X, Y, Z
    }

    public DiningChair findNearby(Location location, double radius) {
        return this.registry.findNearby(location, radius);
    }

    public double getCenterX() {
        return this.zone.getCenterX();
    }

    public double getCenterY() {
        return this.zone.getCenterY();
    }

    public double getCenterZ() {
        return this.zone.getCenterZ();
    }

    public void clear() {
        this.registry.clear();
    }

    public DiningChairRegistry getRegistry() {
        return this.registry;
    }
}