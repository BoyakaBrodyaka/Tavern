package me.boyakabrodyaka.operation.npc.registration;

import org.bukkit.Location;
import org.bukkit.World;

public class NpcRegistrationQueue {

    private static final double STEP_X = 0.0D;
    private static final double STEP_Y = 0.0D;
    private static final double STEP_Z = 1.0D;
    private static final int MAX = 5;

    public Location getQueuePosition(World world, int index) {
        double x = NpcRegistrationCoordinate.TARGET_X.getValue() + STEP_X * index;
        double y = NpcRegistrationCoordinate.TARGET_Y.getValue() + STEP_Y * index;
        double z = NpcRegistrationCoordinate.TARGET_Z.getValue() + STEP_Z * index;

        return new Location(world, x, y, z);
    }

    public int getMax() {
        return MAX;
    }
}