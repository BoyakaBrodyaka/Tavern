package me.boyakabrodyaka.cuisine.grill.station;

import org.bukkit.World;

public class GrillStationFactory {

    private static final String KEY_SUFFIX = "_grill";

    public GrillStation create(World world) {
        String key = world.getName() + KEY_SUFFIX;

        return new GrillStation(
                key,
                GrillStationCoordinate.X.getValue(),
                GrillStationCoordinate.Y.getValue(),
                GrillStationCoordinate.Z.getValue()
        );
    }
}