package me.boyakabrodyaka.cuisine.kitchen.station;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class KitchenStationBlockChecker {

    private static final String STATION_BLOCK_NAME = "TAVERNMOD_CAULDRON";
    private static final Material STATION_MATERIAL = Material.getMaterial(STATION_BLOCK_NAME);

    public boolean isStationBlock(Location location) {
        if (location == null) return false;
        if (STATION_MATERIAL == null) return false;

        World world = location.getWorld();
        if (world == null) return false;

        Block block = location.getBlock();

        return block.getType() == STATION_MATERIAL;
    }
}