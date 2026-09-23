package me.boyakabrodyaka.cuisine.kitchen.station;

import org.bukkit.World;

public class KitchenStationFactory {

    private static final String KEY_SUFFIX = "_kitchen";

    public KitchenStation create(World world) {
        String key = world.getName() + KEY_SUFFIX;

        return new KitchenStation(
                key,
                KitchenStationCoordinate.X.getValue(),
                KitchenStationCoordinate.Y.getValue(),
                KitchenStationCoordinate.Z.getValue()
        );
    }
}