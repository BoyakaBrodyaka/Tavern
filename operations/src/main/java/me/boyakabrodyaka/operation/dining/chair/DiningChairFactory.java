package me.boyakabrodyaka.operation.dining.chair;

import org.bukkit.World;

public class DiningChairFactory {

    private static final String KEY_FORMAT = "%s_chair_%d";

    public DiningChair create(World world, int seat, double x, double y, double z) {
        String key = String.format(KEY_FORMAT, world.getName(), seat);
        return new DiningChair(key, seat, x, y, z);
    }
}