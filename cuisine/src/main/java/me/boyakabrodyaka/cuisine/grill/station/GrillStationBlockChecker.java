package me.boyakabrodyaka.cuisine.grill.station;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Material;

public class GrillStationBlockChecker {

    private static final String GRILL_BLOCK_NAME = "TAVERNMOD_GRILL";

    public boolean isGrillBlock(Location location) {
        if (location == null) return false;

        World world = location.getWorld();
        if (world == null) return false;

        Block block = location.getBlock();
        Material type = block.getType();

        return type.name().equals(GRILL_BLOCK_NAME);
    }
}